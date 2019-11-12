package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test


class HeartbeatTest {

    @Test
    fun `should beat after some elapsed time`() {
        // Given we're listening to the heartbeats
        val events = Events()
        var heartbeats = 0
        events.heartbeatNotification.subscribe { heartbeats = heartbeats.inc() }

        val heartbeat = AcceleratingHeartbeat(events, 10, 1, GlobalScope)

        // When we start a new game
        events.gamePlayingNotification.push(Object())

        // Then the heartbeat should start after some time
        runBlocking { delay(50)}
        assertThat(heartbeats, greaterThan(0))
    }

    fun `should beat more quickly with each beat`() {
        // No way of ensuring this with automation.
    }

    fun `should stop any existing beating before starting`() {
        // No way of ensuring this with automation.
    }

    fun `should restart wait for next beat when skipping a beat`() {
        // Nor this.
    }

    @Test
    fun `should not beat after being stopped`() {
        // Given we're listening to the heartbeats
        val events = Events()

        val heartbeat = AcceleratingHeartbeat(events, 10, 1, GlobalScope)

        // When we start a new game, then stop it
        events.gamePlayingNotification.push(Object())
        events.gameOverNotification.push(Object())


        // Then we should not hear more heartbeats
        var heartbeats = 0
        events.heartbeatNotification.subscribe { heartbeats = heartbeats.inc() }
        runBlocking { delay(50)}
        assertThat(heartbeats, equalTo(0))
    }
}