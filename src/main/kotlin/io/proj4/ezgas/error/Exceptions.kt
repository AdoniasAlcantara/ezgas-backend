package io.proj4.ezgas.error

open class ResourceNotFoundException(message: String) : RuntimeException(message)

class PageNotFoundException(pageNumber: Int) :
        ResourceNotFoundException("The requested page does not exist. Page number was $pageNumber")