package org.wine.productservice.shared.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

class InvalidIdsException(val fieldName: String, val invalidIds: List<String>) : RuntimeException(
    "Invalid $fieldName: ${invalidIds.joinToString()} must be positive integers"
)

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidIdsValidator::class])
annotation class ValidIds(
    val message: String = "Invalid IDs format or value",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val fieldName: String = "ids"
)

class ValidIdsValidator : ConstraintValidator<ValidIds, String?> {
    private lateinit var fieldName: String

    override fun initialize(constraintAnnotation: ValidIds) {
        fieldName = constraintAnnotation.fieldName
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true

        val invalidIds = value.split(",")
            .map { it.trim() }
            .filter { it.toLongOrNull()?.let { id -> id <= 0 } ?: true }

        if (invalidIds.isNotEmpty()) {
            val errorMessage = "Invalid $fieldName=${invalidIds.joinToString()} must be positive integers"
            context?.disableDefaultConstraintViolation()
            context?.buildConstraintViolationWithTemplate(errorMessage)
                ?.addConstraintViolation()
            return false
        }

        return true
    }
}
