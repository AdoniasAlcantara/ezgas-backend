package io.proj4.ezgas.error

import io.proj4.ezgas.dto.ErrorMessageDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFound::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun resourceNotFound(exception: ResourceNotFound, request: HttpServletRequest): ErrorMessageDto {
        return ErrorMessageDto(HttpStatus.NOT_FOUND, request, exception)
    }
}