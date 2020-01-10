package io.proj4.ezgas.util

import javax.servlet.http.HttpServletRequest

val HttpServletRequest.path
    get() = "$requestURI/$queryString"