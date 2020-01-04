package io.proj4.ezgas.dto

import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

class ErrorMessageDto(status: HttpStatus, request: HttpServletRequest, exception: Throwable) {
    val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
    val status: Int = status.value()
    val message: String = exception.message ?: ""
    val path: String = with(request) { "$requestURI/Z$queryString" }
}