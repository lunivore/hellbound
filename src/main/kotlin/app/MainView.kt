package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.com.lunivore.hellbound.app.FrontView
import com.lunivore.hellbound.com.lunivore.hellbound.app.GameView
import tornadofx.*


class MainView : View() {

    val events : Events by di()

    private val gameView = find(GameView::class)
    private val frontView = find(FrontView::class)


    init {
        title = "Hellbound!"
        events.gameStartNotification.subscribe { frontView.root.toBack() }

    }

    override val root = stackpane {
        children.add(gameView.root)
        children.add(frontView.root)
    }
}

