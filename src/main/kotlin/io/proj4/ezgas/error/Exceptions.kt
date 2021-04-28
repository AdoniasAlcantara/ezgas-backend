package io.proj4.ezgas.error

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.server.ResponseStatusException

class ResourceNotFoundException(resourceId: Any) :
    ResponseStatusException(NOT_FOUND, "Resource not found: $resourceId")

class PageNotFoundException(pageNumber: Int) :
    ResponseStatusException(NOT_FOUND, "The requested page does not exist. Page number was $pageNumber")
