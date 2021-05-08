package io.proj4.ezgas.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Duration
import java.time.LocalDateTime.now

@RestController
@RequestMapping("/echo")
class EchoController {
    private val startTime = now()

    @Suppress("unused")
    @GetMapping
    fun echo() = object {
        val uptime = Duration
            .between(startTime, now())
            .toSeconds()
    }
}
