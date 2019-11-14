package com.lunivore.hellbound

import com.google.inject.Guice
import com.lunivore.hellbound.engine.Heartbeat
import com.lunivore.hellbound.model.GameSize
import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CanPlayTheGame : Scenario() {

    private val events: Events = Events()

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

    @Test
    fun `The shape should move down on heartbeat`() {
        // Given a game that's just started
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        // When the heart beats
        Stirry.runOnPlatform { events.heartbeatNotification.push(Object()) }

        // Then the initial T-shape should be moved down
        val expectedTopOfGrid =
            """
            ...........
            ....XXX....
            .....X.....
            """
        val expectedGrid = appendEmptyGridRows(expectedTopOfGrid)
        assertThat(convertGridToString(gameGrid), equalTo(expectedGrid))
    }

    @Test
    fun `If the shape collides with the bottom or another shape it should become junk`() {
        // Given a game that's just started
        val gameGrid = makeNewGameReady()
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.R)

        // When we drop the shape, then drop the next shape
        pressKey(gameView, KeyCode.SPACE)
        pressKey(gameView, KeyCode.SPACE)
        Stirry.runOnPlatform { events.heartbeatNotification.push(Object()) }

        // Then they should fall to the bottom and the next shape should appear
        val expectedGrid = nl +
            """
            ....XX.....
            .....XX....
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ...........
            ....XXX....
            .....X.....
            """.trimIndent() + nl
        assertThat(convertGridToString(gameGrid), equalTo(expectedGrid))
    }

    private fun pressKey(origin: Node, key: KeyCode) {
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

    private fun appendEmptyGridRows(topThreeLines: String) : String {
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
}
