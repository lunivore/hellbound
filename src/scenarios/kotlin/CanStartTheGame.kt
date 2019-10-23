package com.lunivore.hellbound

import com.lunivore.hellbound.com.lunivore.hellbound.Scenario
import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.scene.Group
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import org.junit.After
import org.junit.Before
import org.junit.Test
import tornadofx.*

class CanStartTheGame : Scenario() {

    @Before
    fun `Starting Stirry`() {
        Stirry.initialize()
        val hellbound = Hellbound()
        Stirry.launchApp(hellbound)
    }

    @After
    fun `Stopping Stirry`() {
        // TODO: Add this to Stirry's Stop or find out how to make this work properly in TornadoFX -
        // singleAssign() is not using the app as its scope!
        Stirry.stage?.scene?.root = HBox()
        Stirry.stop()
    }

    @Test
    fun `The grid should start empty`() {
        val gameGrid = makeNewGameReady()

        gameGrid.children.forEach {
            val color = (it as Rectangle).fill.toProperty().get()!!
            assertThat(color, equalTo(Color.BLACK as Paint))
        }
    }

    @Test
    fun `Pressing a key should start the game`() {
        val gameGrid = makeNewGameReady()
        Stirry.findRoot().fireEvent(KeyEvent(KeyEvent.KEY_RELEASED,
            KeyCode.A.toString(), KeyCode.A.toString(),
            KeyCode.A, false, false, false, false))

        assertThat(convertGridToString(gameGrid), equalTo(nl + """
            ....TTT....
            .....T.....
            ...........
            """.trimIndent() +
                (1..20).map {nl + "..........." }.joinToString("") + nl))
    }

    private fun convertGridToString(actualGrid: Group): String {
        var numOfCols : Int = with (actualGrid.children.last() as Rectangle) {(x / width).toInt()}

        val builder = StringBuilder()
        builder.append(nl)
        for(child in actualGrid.children) {
            val square = child as Rectangle
            appendRepresentationAndNLIfRequired(square, builder, numOfCols)
        }
        return builder.toString()
    }

    private fun appendRepresentationAndNLIfRequired(
        square: Rectangle,
        builder: StringBuilder,
        numOfCols: Int
    ) {
        builder.append(getStringRepresentationOf(square.fill!!))
        val col = (square.x / square.width).toInt()
        if (col == numOfCols) {
            builder.append(nl)
        }
    }

    private fun getStringRepresentationOf(color: Paint): Char {
        return if(Color.BLACK == color) '.' else 'X'
    }
}
