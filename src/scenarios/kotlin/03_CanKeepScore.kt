package com.lunivore.hellbound

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import org.junit.Test

class CanKeepScore : Scenario() {

    @Test
    fun `Should give 10 points for dropping a shape`() {
        // Given a game that's just started
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        // When we drop the shape, then drop the next shape
        pressKey(gameView, KeyCode.SPACE)
        Stirry.runOnPlatform { events.heartbeatNotification.push(Object()) }
        pressKey(gameView, KeyCode.SPACE)
        Stirry.runOnPlatform { events.heartbeatNotification.push(Object()) }

        // Then the score should be 20
        val label = Stirry.findInRoot<Label> { it.id == "scoreLabel" }.value

        assertThat(label.text, equalTo("20"))

    }

    @Test
    fun `Should give 100 points for a line`() {
        // Given a game
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        // In which a sequence of moves has been carried out to make a line
        val p : (KeyCode) -> Unit = { pressKey(gameView, it) }
        val h : () -> Unit = { Platform.runLater { events.heartbeatNotification.push(Object()) } }
        val sequenceOfMovesToMakeALine = """
            EEDDDD h
            EDDD h
            EDDDDD h
            D h
            E h
            QAAA h
            QAAAA 
        """.trimIndent().replace("\n", "")

        sequenceOfMovesToMakeALine.forEach {
            when(it) {
                'h' -> h()
                ' ' -> p(KeyCode.SPACE)
                else -> p(KeyCode.valueOf(it.toString()))
            }
        }
        Stirry.waitForPlatform()

        // And for which the score is recorded (because I can't be bothered to calculate it)
        val scoreLabel = Stirry.findInRoot<Label> { it.id == "scoreLabel" }.value
        val scoreWas = scoreLabel.text.toInt()

        // When we make a heartbeat
        h()
        Stirry.waitForPlatform()

        // Then the score should have gone up by 100 + 10 for new shape
        val scoreIs = scoreLabel.text.toInt()

        assertThat(scoreIs - scoreWas, equalTo(110))
    }
}