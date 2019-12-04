package com.lunivore.hellbound

import com.google.inject.Guice
import com.lunivore.hellbound.engine.Heartbeat
import com.lunivore.hellbound.model.GameSize
import com.lunivore.stirry.Stirry
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock

abstract class Scenario {

    protected val nl = "\n"
    protected val events: Events = Events()

    protected fun makeNewGameReady(): Group {
        val button = Stirry.findInRoot<Button> { it.id == "newGameButton" }.value
        Stirry.runOnPlatform {button.fire()}
        val gameGrid = Stirry.findInRoot<Group> { it.id == "gameGrid" }.value
        return gameGrid
    }

    protected fun pressKey(origin: Node, key: KeyCode) {
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

    protected fun convertGridToString(actualGrid: Group): String {
        var numOfCols : Int = with (actualGrid.children.last() as Rectangle) {(x / width).toInt()}

        val builder = StringBuilder()
        builder.append(nl)
        for(child in actualGrid.children) {
            val square = child as Rectangle
            appendRepresentationAndNLIfRequired(square, builder, numOfCols)
        }
        return builder.toString()
    }

    protected fun appendEmptyGridRows(topThreeLines: String) : String {
        val additionalRows = GameSize().rows - 3
        return nl + topThreeLines.trimIndent() +
                (0..additionalRows).map { nl + "..........." }.joinToString("") + nl
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

    @Before
    fun `Starting Stirry`() {
        System.out.println("Starting Stirry")
        Stirry.initialize()
        val fixedSeed = 42L
        val unusedBecauseWeFakeTheHeartbeat = mock(Heartbeat::class.java)
        val injector = Guice.createInjector(InjectorModule(fixedSeed, events, unusedBecauseWeFakeTheHeartbeat))
        val hellbound = Hellbound(injector)
        Stirry.launchApp(hellbound)
    }

    @After
    fun `Stopping Stirry`() {
        System.out.println("Stopping Stirry")
        // TODO: Add this to Stirry's Stop or find out how to make this work properly in TornadoFX -
        // singleAssign() is not using the app as its scope!
        Stirry.stage?.scene?.root = HBox()
        Stirry.stop()
    }

}
