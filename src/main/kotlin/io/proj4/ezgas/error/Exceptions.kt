package io.proj4.ezgas.error

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.server.ResponseStatusException

class ResourceNotFoundException(resourceId: Any) :
    ResponseStatusException(NOT_FOUND, "Resource not found: $resourceId")

class InvalidQueryException(message: String) :
    ResponseStatusException(BAD_REQUEST, message)
