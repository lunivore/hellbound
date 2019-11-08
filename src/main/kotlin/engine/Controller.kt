package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.PlayerMove

interface GameFactory {
    fun create(): Game
}

class Controller(events: Events, gameFactory: GameFactory) {

    var WELCOME: State = object : DefaultState() {
        override fun getReady() {
            events.gameReadyNotification.push(Object())
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
    }

    private var state = WELCOME
    private var game: Game = gameFactory.create()

    init {
        events.gameReadyRequest.subscribe {state.getReady()}
        events.playerMoveRequest.subscribe {
            state.move(it)
        }
    }





}
