package com.lunivore.hellbound

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import org.junit.Test

class CanPlayTheGame : Scenario() {

    @Test
    fun `The grid should start empty`() {
        // Given a new game
        val gameGrid = makeNewGameReady()

        // Then the grid should be empty
        val expectedTopOfGrid =
            """
            ...........
            ...........
            ...........
            """
        val expectedGrid = appendEmptyGridRows(expectedTopOfGrid)
        assertThat(convertGridToString(gameGrid), equalTo(expectedGrid))
    }

    @Test
    fun `Pressing a key should start the game`() {
        // Given a new game that's ready to play
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value

        // When we press any key
        pressKey(gameView, KeyCode.R)

        // Then it should start playing
        val expectedTopOfGrid =
            """
            ....XXX....
            .....X.....
            ...........
            """
        val expectedGrid = appendEmptyGridRows(expectedTopOfGrid)
        assertThat(convertGridToString(gameGrid), equalTo(expectedGrid))
    }

    @Test
    fun `ASDQE should move and rotate the shape`() {

        // NOTE: We don't need to test all the keys here; this is just an example.
        // Given a game that's just started
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        // When we press D, E
        pressKey(gameView, KeyCode.D)
        pressKey(gameView, KeyCode.E)

        // Then the initial T-shape should be moved right and rotated clockwise
        val expectedTopOfGrid =
            """
            ......X....
            .....XX....
            ......X....
            """
        val expectedGrid = appendEmptyGridRows(expectedTopOfGrid)
        assertThat(convertGridToString(gameGrid), equalTo(expectedGrid))
    }


}
