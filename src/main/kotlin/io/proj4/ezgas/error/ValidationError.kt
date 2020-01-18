package io.proj4.ezgas.error

import com.fasterxml.jackson.annotation.JsonUnwrapped

class ValidationError(
        @JsonUnwrapped
        val error: Error,
        val fieldErrors: List<FieldError>
)