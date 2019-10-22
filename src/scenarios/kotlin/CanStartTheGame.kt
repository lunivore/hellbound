package com.lunivore.hellbound

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import org.junit.Before
import org.junit.Test
import tornadofx.*

class CanStartTheGame {

    @Before
    fun `Starting Stirry`() {
        Stirry.initialize()
        val hellbound = Hellbound()
        Stirry.launchApp(hellbound)
    }

    @Test
    fun `The grid should start empty`() {
        Stirry.findInRoot<Button> {  it.id == "newGameButton" }.value.fire()
        val gameGrid = Stirry.findInRoot<Group> {  it.id == "gameGrid" }.value

        gameGrid.children.forEach {
            val color = (it as Rectangle).fill.toProperty().get()!!
            assertThat(color, equalTo(Color.BLACK as Paint))
        }
    }

    @Test
    fun `Pressing a key should start the game`() {

    }
}