package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.com.lunivore.hellbound.app.FrontView
import com.lunivore.hellbound.com.lunivore.hellbound.app.GameView
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.input.KeyEvent
import org.apache.logging.log4j.LogManager
import tornadofx.*

class MainView : View() {

    val events : Events by di()
    val keycodeTranslator : KeycodeTranslator by di()

    private val gameView = find(GameView::class)
    private val frontView = find(FrontView::class)


    companion object {
        val logger = LogManager.getLogger()
    }

    init {
        logger.info("Constructing main view")
        title = "Hellbound!"
        events.gameReadyNotification.subscribe {
            frontView.root.toBack()
            gameView.root.requestFocus()
        }


    }

    override val root = stackpane {
        addEventFilter(KeyEvent.KEY_PRESSED) { events.playerMoveRequest.push(keycodeTranslator.translate(it.code)) }
        children.add(gameView.root)
        children.add(frontView.root)
    }
}