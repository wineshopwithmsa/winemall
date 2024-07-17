package org.wine.productservice.wine.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.wine.productservice.wine.dto.WineResponseDto
import org.wine.productservice.wine.repository.WineRepository

@Service
class WineService @Autowired constructor(
    private val wineRepository: WineRepository,
) {
    fun getWinesForSeller(): List<WineResponseDto> {
        return wineRepository.findAll().map { wine -> WineResponseDto.fromResponseDtoWine(wine) }
    }

    // TODO: consumer
//    fun getWinesForConsumer(): List<WineResponseDto> {
//    }
}