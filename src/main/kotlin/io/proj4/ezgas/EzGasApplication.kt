package io.proj4.ezgas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EzGasBackendApplication

fun main(args: Array<String>) {
	runApplication<EzGasBackendApplication>(*args)
}
