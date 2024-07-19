package org.wine.productservice.wine.exception

import org.wine.productservice.config.BadRequestException

class InvalidCategoryException private constructor(message: String) : BadRequestException(message) {
    constructor(categoryIds: Set<Long>) : this("Invalid category ids: $categoryIds")
}

class InvalidRegionException private constructor(message: String) : BadRequestException(message) {
    constructor(regionId: Long) : this("Invalid region id: $regionId")
}

object WineValidationMessages {
    const val NAME_BLANK = "Name cannot be blank"
    const val NAME_LENGTH = "Name must be between 2 and 100 characters long"
    const val DESCRIPTION_BLANK = "Description cannot be blank"
    const val DESCRIPTION_LENGTH = "Description must be at most 5000 characters long"
}

class InvalidRequestException(message: String) : BadRequestException(message)
