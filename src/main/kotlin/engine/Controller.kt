package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.PlayerMove
import org.apache.logging.log4j.LogManager

interface GameFactory {
    fun create(): Game
}

class Controller(events: Events, gameFactory: GameFactory) {
    companion object {
        val logger = LogManager.getLogger()
    }

    var WELCOME: State = object : DefaultState() {
        override fun getReady(size: GameSize) {
            events.gameReadyNotification.push(size)
            state = READY
        }
    }

    var READY: State = object : DefaultState() {
        override fun move(playerMove: PlayerMove) {
            events.gamePlayingNotification.push(Object())
            game.startPlaying()
            state = PLAYING
        }
    }

    var PLAYING: State = object : DefaultState() {
        override fun move(playerMove: PlayerMove) {
            game.move(playerMove)
        }
        override fun heartbeat() {
            game.heartbeat()
        }
    }

    private var state = WELCOME
    private var game: Game = gameFactory.create()

    init {
        logger.info("Initializing controller")
        events.gameReadyRequest.subscribe {state.getReady(it)}
        events.playerMoveRequest.subscribe {
            state.move(it)
        }
        events.heartbeatNotification.subscribe {
            state.heartbeat()
        }
    }
}
