package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import javafx.application.Platform
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun pressKey(origin: Node, key: KeyCode) {
    Stirry.runOnPlatform {
        origin.fireEvent(
            KeyEvent(
                KeyEvent.KEY_PRESSED,
                key.toString(), key.toString(),
                key, false, false, false, false
            )
        )
    }
}

class KeyPressPageObject(private val world: World) {
    fun presses_any_key() {
        val overlayView = Stirry.findInRoot<BorderPane> { it.id == "overlayView" }.value
        pressKey(overlayView, KeyCode.R)
        runBlocking { delay(50) }
        Stirry.waitForPlatform()
    }

    fun presses_the_sequence(sequenceOfMoves: String): KeyPressPageObject {
        val sanitizedSequence =
            if (sequenceOfMoves.contains("\n")) sequenceOfMoves.trimIndent().replace("\n", "") else sequenceOfMoves

        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value

        val p : (KeyCode) -> Unit = { pressKey(gameView, it) }
        val h : () -> Unit = { Platform.runLater { world.events.heartbeatNotification.push(Object()) } }
        sanitizedSequence.forEach {
            when(it) {
                'h' -> {
                    h()
                }
                ' ' -> p(KeyCode.SPACE)
                else -> p(KeyCode.valueOf(it.toString()))
            }
        }
        runBlocking { delay(50) }
        Stirry.waitForPlatform()
        return this
    }

    fun and_the_current_shape(sequenceOfMoves: String) {
        presses_the_sequence(sequenceOfMoves)
    }

}
