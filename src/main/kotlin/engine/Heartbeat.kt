package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.PlayerMove
import kotlinx.coroutines.*

interface Heartbeat {
    fun start()
}

class AcceleratingHeartbeat(
        private val events: Events,
        private val initialTimeBetweenBeats: Long,
        private val timeToReduce: Long
) : Heartbeat {

    private var currentBeat: Job = GlobalScope.launch {}
    private var timeBetweenBeats: Long = initialTimeBetweenBeats
    private var beating = true

    init {
        events.gamePlayingNotification.subscribe {
            start()
        }
        events.gameOverNotification.subscribe {
            currentBeat.cancel()
            timeBetweenBeats = initialTimeBetweenBeats
        }
        events.playerMoveRequest.subscribe {
            if (it == PlayerMove.DOWN) {
                currentBeat.cancel()
                start()
            }
        }
    }

    override fun start() {
        currentBeat = GlobalScope.launch {
            while(isActive && timeBetweenBeats > 0) {
                delay(timeBetweenBeats)
                if(isActive) {
                    events.heartbeatNotification.push(Object())
                    timeBetweenBeats = timeBetweenBeats - timeToReduce
                }
            }
        }
    }
}