package org.wine.productservice.wine.dto

data class WineSaleResponse(val wine: WineSaleDto)
data class WineSalesResponse(val wine: List<WineSaleDto>)