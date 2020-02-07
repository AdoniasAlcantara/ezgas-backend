package io.proj4.ezgas.util

import javax.servlet.http.HttpServletRequest

val HttpServletRequest.path: String
    get() {
        var path = requestURI

        if (!queryString.isNullOrBlank()) {
            path += "?$queryString"
        }

        return path
    }