package com.lunivore.hellbound.app

import OverylayView
import com.lunivore.hellbound.Events
import com.lunivore.hellbound.app.GameView
import com.lunivore.hellbound.app.WelcomeView
import javafx.scene.input.KeyEvent
import org.apache.logging.log4j.LogManager
import tornadofx.View
import tornadofx.stackpane

class MainView : View() {

    val events : Events by di()
    val keycodeTranslator : KeycodeTranslator by di()

    private val gameView = find(GameView::class)
    private val overlayView = find(OverylayView::class)
    private val frontView = find(WelcomeView::class)


    companion object {
        val logger = LogManager.getLogger()
    }

    init {
        logger.info("Constructing main view")
        title = "Hellbound!"
        events.gameReadyNotification.subscribe {
            frontView.root.toBack()
            overlayView.root.requestFocus()
        }
        events.gamePlayingNotification.subscribe {
            overlayView.root.toBack()
            gameView.root.requestFocus()
        }
        events.gameOverNotification.subscribe {
            overlayView.root.toFront()
            overlayView.root.requestFocus()
        }
        events.showWelcomeNotification.subscribe {
            frontView.root.toFront()
            frontView.root.requestFocus()
        }
    }

    override val root = stackpane {
        addEventFilter(KeyEvent.KEY_PRESSED) { events.playerMoveRequest.push(keycodeTranslator.translate(it.code)) }
        children.add(gameView.root)
        children.add(overlayView.root)
        children.add(frontView.root)
    }
}