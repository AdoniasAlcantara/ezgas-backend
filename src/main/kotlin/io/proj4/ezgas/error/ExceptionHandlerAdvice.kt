package io.proj4.ezgas.error

import io.proj4.ezgas.utils.errorDetails
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.OffsetDateTime

@RestControllerAdvice
class ExceptionHandlerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest
    ): ResponseEntity<*> {
        val arg = ex.name
        val requiredType = ex.requiredType?.simpleName
        val value = ex.value
        val message = "Required type for argument $arg is $requiredType but the given value was '$value'"

        return newErrorResponse(HttpHeaders(), BAD_REQUEST, request, message)
    }

    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) = newErrorResponse(headers, status, request, "Validation error", ex.bindingResult.errorDetails)

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) = newErrorResponse(headers, status, request, ex.message)

    private fun newErrorResponse(
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
        message: String? = null,
        details: List<ErrorDetail>? = null
    ): ResponseEntity<Any> {
        request as ServletWebRequest

        val errorResponse = ErrorResponse(
            timestamp = OffsetDateTime.now(),
            status = status.value(),
            error = status.reasonPhrase,
            message = message ?: "unspecified",
            path = request.request.requestURI,
            details = details
        )

        return ResponseEntity(errorResponse, headers, status)
    }
}
