package com.lunivore.hellbound

import com.lunivore.stirry.Stirry
import javafx.scene.Group
import javafx.scene.control.Button
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import org.junit.Assert.assertEquals
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
            assertEquals((it as Rectangle).fill.toProperty().get(), Color.BLACK)
        }

    }
}