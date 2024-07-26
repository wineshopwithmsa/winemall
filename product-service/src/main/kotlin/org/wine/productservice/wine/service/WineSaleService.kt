package org.wine.productservice.wine.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.productservice.auth.AuthService
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
        var wines = requestDto.ids?.takeIf { it.isNotEmpty() }?.let {
            wineSaleRepository.findAllByWineSaleIdIn(it)
        } ?: wineSaleRepository.findAll()

        return wines.stream()
            .map{wineSaleMapper.toWineSaleDto(it)}
            .collect(Collectors.toList())
    }
}
