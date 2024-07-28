package org.wine.productservice.wine.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.productservice.auth.AuthService
import org.wine.productservice.wine.dto.WineSaleCreateRequestDto
import org.wine.productservice.wine.dto.WineSaleDto
import org.wine.productservice.wine.dto.WineSalesRequestDto
import org.wine.productservice.wine.mapper.WineSaleMapper
import org.wine.productservice.wine.repository.WineSaleRepository
import java.util.stream.Collectors

@Service
class WineSaleService @Autowired constructor(
    private val authService: AuthService,
    private val wineSaleMapper: WineSaleMapper,

    private val wineSaleRepository: WineSaleRepository,
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
    fun addWineSale(requestDto: WineSaleCreateRequestDto): WineSaleDto {
        val userId = authService.getAccountId()
        val wineSale = wineSaleMapper.toWineSale(requestDto)

        wineSale.sellerId = userId

        val savedWineSale = wineSaleRepository.save(wineSale)

        return wineSaleMapper.toWineSaleDto(savedWineSale)
    }
}
