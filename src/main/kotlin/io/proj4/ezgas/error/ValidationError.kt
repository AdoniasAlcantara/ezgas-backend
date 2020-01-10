package io.proj4.ezgas.error

class ValidationError(
        status: Int,
        path: String,
        message: String,
        val fieldErrors: List<FieldError>
) : Error(status, path, message) {
}