package org.wine.productservice.wine.dto

data class WineResponse(val wine: WineDto)
data class WinesResponse(val wines: List<WineDto>)