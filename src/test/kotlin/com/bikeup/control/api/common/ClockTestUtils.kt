package com.bikeup.control.api.common

import io.mockk.every
import io.mockk.mockkStatic
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

object ClockTestUtils {

    fun mockClock() {
        val clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"))
        mockkStatic(Clock::class)
        every { Clock.systemUTC() } returns clock
    }
}