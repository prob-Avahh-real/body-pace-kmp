package com.bodypace.model

import kotlin.test.Test
import kotlin.test.assertEquals

class RunLogTest {
    @Test
    fun `pace calculation for 10km in 50 minutes`() {
        val log = RunLog(0L, 50, 10.0, 5, emptyList())
        assertEquals("5'00\"", log.pacePerKm)
    }

    @Test
    fun `pace for zero distance`() {
        val log = RunLog(0L, 0, 0.0, 0, emptyList())
        assertEquals("--", log.pacePerKm)
    }

    @Test
    fun `pace for half marathon in 2 hours`() {
        val log = RunLog(0L, 120, 21.0975, 7, emptyList())
        assertEquals("5'41\"", log.pacePerKm)
    }

    @Test
    fun `feeling summary for low RPE`() {
        val log = RunLog(0L, 30, 5.0, 2, emptyList())
        assertEquals("轻松", log.feelingSummary)
    }

    @Test
    fun `feeling summary for moderate RPE`() {
        val log = RunLog(0L, 30, 5.0, 5, emptyList())
        assertEquals("适中", log.feelingSummary)
    }

    @Test
    fun `feeling summary for high RPE`() {
        val log = RunLog(0L, 30, 5.0, 8, emptyList())
        assertEquals("极限", log.feelingSummary)
    }
}
