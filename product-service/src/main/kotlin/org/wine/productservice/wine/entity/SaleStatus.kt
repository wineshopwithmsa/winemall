package org.wine.productservice.wine.entity

enum class SaleStatus (
    val status: Int,
    val description: String ) {
    SALE_UPCOMING(status=0, description = "판매예정"),
    SALE_ACTIVE(status=1, description = "판매중"),
    SALE_ENDED(status=2, description = "판매종료"),
    SALE_PAUSED(status=3, description = "판매중지"),
}