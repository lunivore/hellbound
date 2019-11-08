package com.lunivore.hellbound.app

import com.lunivore.hellbound.model.PlayerMove
import javafx.scene.input.KeyCode

class KeycodeTranslator {
    fun translate(code: KeyCode) = when(code) {
        KeyCode.A -> PlayerMove.LEFT
        KeyCode.D -> PlayerMove.RIGHT
        KeyCode.S -> PlayerMove.DOWN
        KeyCode.SPACE -> PlayerMove.DROP
        KeyCode.Q -> PlayerMove.WIDDERSHINS
        KeyCode.E -> PlayerMove.CLOCKWISE
        else -> PlayerMove.UNMAPPED
    }
}
