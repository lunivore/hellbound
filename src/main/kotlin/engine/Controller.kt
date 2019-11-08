package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.com.lunivore.hellbound.engine.Game
import javafx.scene.input.KeyCode

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
        override fun keyPress(keyCode: KeyCode) {
            events.gamePlayingNotification.push(Object())
            game.startPlaying()
            state = PLAYING
        }
    }

    var PLAYING: State = object : DefaultState() {
        override fun keyPress(keyCode: KeyCode) {
            game.keyPressed(keyCode)
        }
    }

    private var state = WELCOME
    private var game: Game = gameFactory.create()

    init {
        events.gameReadyRequest.subscribe {state.getReady()}
        events.keyPressNotification.subscribe {
            state.keyPress(it)
        }
    }





}
