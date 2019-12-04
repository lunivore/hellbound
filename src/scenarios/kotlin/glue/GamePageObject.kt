package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import com.lunivore.stirry.find
import com.lunivore.stirry.fireAndStir
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import javafx.application.Platform
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue

class GamePageObject(private val world: World, private val keyPressPageObject: KeyPressPageObject) {

    fun thats_just_started() {
        makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }
            .value
        pressKey(gameView, KeyCode.R)
    }

    private fun makeNewGameReady(): Group {
        val button = Stirry.findInRoot<Button> { it.id == "newGameButton" }.value
        Stirry.runOnPlatform { button.fire() }
        Stirry.waitForPlatform()
        runBlocking { delay(50)}
        val gameGrid = Stirry.findInRoot<Group> { it.id == "gameGrid" }.value
        return gameGrid
    }

    fun with_existing_play(sequenceOfMoves: String) : KeyPressPageObject {
        thats_just_started()
        return keyPressPageObject.presses_the_sequence(sequenceOfMoves.trimIndent())
    }

    fun which_is_above_the_high_scores() : GamePageObject {
        thats_just_started()
        keyPressPageObject.presses_the_sequence("""
            EEDDDD h
            EDDD h
            EDDDDD h
            D h
            E h
            QAAA h
            QAAAA h
            QD h
            QAAA h
        """)
        return this
    }

    fun and_with_a_play(): GamePageObject {
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value

        (1..9).forEach {
            Platform.runLater { world.events.heartbeatNotification.push(Object()) }
            pressKey(gameView, KeyCode.SPACE)
        }
        return this
    }

    fun that_is_about_to_lose() {
        // Deliberately empty; just descriptive
    }

    fun should_prompt_for_the_player_name() {
        val dialog = Stirry.findModalDialog()
        val playerNameField = dialog.find<TextField> { true }.value
        Stirry.runOnPlatform { playerNameField.text = "Luni" }
        runBlocking { delay(1000) }
        dialog.find<Button>({it.isDefaultButton}).value.fireAndStir()

    }

    fun that_is_new() {
        makeNewGameReady()
    }

    fun run_to_the_top() {
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        (1..9).forEach {
            Platform.runLater { world.events.heartbeatNotification.push(Object()) }
            pressKey(gameView, KeyCode.SPACE)
        }
        Stirry.waitForPlatform()
    }

    fun should_be_over() {
        val overlayView = Stirry.findInRoot<BorderPane> { it.id == "overlayView" }.value
        assertTrue(overlayView.isFocused)
        val message = Stirry.findInRoot<Label> { it.id=="overlayMessageLabel" }.value
        assertThat(message.text, containsSubstring("Game over"))
    }


}