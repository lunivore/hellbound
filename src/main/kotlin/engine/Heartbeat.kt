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
    private val timeToReduce: Long,
    private val coroutineScope: CoroutineScope
) : Heartbeat {

    private var shouldInterrupt : (PlayerMove) -> Unit = {}

    private var currentBeat: Job = GlobalScope.launch {}
    private var timeBetweenBeats: Long = initialTimeBetweenBeats
    private var debugInc = 0

    init {
        events.gamePlayingNotification.subscribe {
            currentBeat.cancel()
            start()
            shouldInterrupt = ::interruptHeartbeatOnDropOrDown
        }
        events.gameOverNotification.subscribe {
            currentBeat.cancel()
            shouldInterrupt = {}
            timeBetweenBeats = initialTimeBetweenBeats
        }
        events.playerMoveRequest.subscribe {
            getInterruptState()(it)
        }
    }

    private fun getInterruptState(): (PlayerMove) -> Unit {
        return shouldInterrupt
    }

    private fun interruptHeartbeatOnDropOrDown(it: PlayerMove) {
        if (it == PlayerMove.DOWN || it == PlayerMove.DROP) {
            currentBeat.cancel()
            start()
        }
    }

    override fun start() {
        currentBeat = GlobalScope.launch(Dispatchers.Main) {
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