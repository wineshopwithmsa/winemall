package org.wine.productservice.wine.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.wine.productservice.auth.AuthService
import org.wine.productservice.kafka.event.CheckStockEvent
import org.wine.productservice.kafka.event.StockRollbackEvent
import org.wine.productservice.wine.dto.*
import org.wine.productservice.wine.exception.WineSaleNotFoundException
import org.wine.productservice.wine.mapper.WineSaleMapper
import org.wine.productservice.wine.repository.WineRepository
import org.wine.productservice.wine.repository.WineSaleRepository
import java.util.stream.Collectors

@Service
class WineSaleService @Autowired constructor(
    private val authService: AuthService,
    private val wineSaleMapper: WineSaleMapper,
    private val wineSaleRepository: WineSaleRepository,
    private val wineRepository: WineRepository,
){
    fun getWineSales(requestDto: WineSalesRequestDto): List<WineSaleDto> {
        val wineSales = requestDto.ids?.takeIf { it.isNotEmpty() }?.let {
            wineSaleRepository.findAllByWineSaleIdIn(it)
        } ?: wineSaleRepository.findAll()

        return wineSales.stream()
            .map { wineSaleMapper.toWineSaleDto(it) }
            .collect(Collectors.toList())
    }

    @Transactional
    fun checkStockAndSubtractStock(event: CheckStockEvent): Int{
        var totalPrice = 0;
        wineSaleRepository.findAllById(event.wineOrderList.map{it.wineSaleId}).forEach {
             val wineSaleId = it.wineSaleId
             var quantity = 0

             event.wineOrderList.forEach {
                 if (wineSaleId.equals(it.wineSaleId)) {
                     quantity = it.quantity
                 }
             }

             totalPrice += it.subtract(quantity)
         }

        return totalPrice
    }

    @Transactional
    fun incrementStock(event: StockRollbackEvent){
        wineSaleRepository.findAllById(event.wineOrderList.map{it.wineSaleId}).forEach {
            val wineSaleId = it.wineSaleId
            var quantity = 0

            event.wineOrderList.forEach {
                if (wineSaleId.equals(it.wineSaleId)) {
                    quantity = it.quantity
                }
            }

            it.increment(quantity)
        }
    }

    @Transactional
    fun addWineSale(requestDto: WineSaleCreateRequestDto, headers: HttpHeaders): WineSaleDto {
        val userId = authService.getAccountId(headers)
        val wineSale = wineSaleMapper.toWineSale(requestDto)

        wineSale.sellerId = userId

        val savedWineSale = wineSaleRepository.save(wineSale)

        return wineSaleMapper.toWineSaleDto(savedWineSale)
    }

    @Transactional
    fun updateWineSale(wineSaleId: Long, requestDto: WineSaleUpdateRequestDto): WineSaleDto {
        validateWineUpdateRequestDto(requestDto)

        val wineSale = wineSaleRepository.findById(wineSaleId)
            .orElseThrow { WineSaleNotFoundException(wineSaleId) }

        requestDto.price?.let { wineSale.price = it }
        requestDto.saleStartTime?.let { wineSale.saleStartTime = it }
        requestDto.saleEndTime?.let { wineSale.saleEndTime = it }

        val savedWineSale = wineSaleRepository.save(wineSale)
        return wineSaleMapper.toWineSaleDto(savedWineSale)
    }

    fun validateWineUpdateRequestDto(requestDto: WineSaleUpdateRequestDto) {

    }
}
