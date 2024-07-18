package org.wine.productservice.wine.exception

import org.wine.productservice.exception.NotFoundException

class CategoryNotFoundException private constructor(message: String) : NotFoundException(message) {
    constructor(categoryIds: Set<Long>) : this("Categories not found with ids: $categoryIds")
}

class RegionNotFoundException private constructor(message: String) : NotFoundException(message) {
    constructor(regionId: Long) : this("Region not found with id: $regionId")
}

class WineNotFoundException private constructor(message: String) : NotFoundException(message) {
    constructor(wineId: Long) : this("Wine not found with id: $wineId")
}
