package org.wine.productservice.wine.exception

import org.wine.productservice.config.NotFoundException

class CategoryNotFoundException (message: String) : NotFoundException(message) {
    constructor(categoryIds: Set<Long>) : this("Categories not found with ids: $categoryIds")
}

class RegionNotFoundException (message: String) : NotFoundException(message) {
    constructor(regionId: Long) : this("Region not found with id: $regionId")
}

class WineNotFoundException (message: String) : NotFoundException(message) {
    constructor(wineId: Long) : this("Wine not found with id: $wineId")
}
