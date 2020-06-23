package io.proj4.ezgas.utils

import io.proj4.ezgas.error.ErrorDetail
import org.springframework.validation.BindingResult

val BindingResult.errorDetails
    get() = fieldErrors.map {
        ErrorDetail(
                field = it.field,
                error = it.defaultMessage ?: "unspecified",
                value = it.rejectedValue
        )
    }