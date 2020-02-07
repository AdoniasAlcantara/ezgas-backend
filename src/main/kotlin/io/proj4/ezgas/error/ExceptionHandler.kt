package io.proj4.ezgas.error

import io.proj4.ezgas.util.path
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFound(exception: ResourceNotFoundException, request: HttpServletRequest): Error {
        return Error(
                HttpStatus.NOT_FOUND.value(),
                request.path,
                exception.localizedMessage
        )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingParameter(exception: MissingServletRequestParameterException, request: HttpServletRequest): Error {
        return Error(
                HttpStatus.BAD_REQUEST.value(),
                request.path,
                exception.localizedMessage
        )
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolation(exception: ConstraintViolationException, request: HttpServletRequest): Error {
        return Error(
                HttpStatus.BAD_REQUEST.value(),
                request.path,
                exception.localizedMessage
        )
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindError(exception: BindException, request: HttpServletRequest): Error {
        return ValidationError(
                HttpStatus.BAD_REQUEST.value(),
                request.path,
                "Validation error",
                exception.fieldErrors.map {
                    FieldError(
                            it.field,
                            it.defaultMessage ?: "unspecified",
                            it.rejectedValue
                    )
                }
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleTypeMismatch(exception: MethodArgumentTypeMismatchException, request: HttpServletRequest): Error {
        return Error(
                HttpStatus.BAD_REQUEST.value(),
                request.path,
                exception.localizedMessage
        )
    }
}