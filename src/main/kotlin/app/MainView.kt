package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.com.lunivore.hellbound.app.FrontView
import com.lunivore.hellbound.com.lunivore.hellbound.app.GameView
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.View
import tornadofx.singleAssign
import tornadofx.tab
import tornadofx.tabpane

class MainView : View() {

    val events : Events by di()

    init {
        title = "Hellbound!"
        events.gameStartNotification.subscribe { gameTabPane.selectionModel.select(gameTab)}
    }

    private var gameTabPane : TabPane by singleAssign()
    private var gameTab : Tab by singleAssign()

    private val frontView = find(FrontView::class)
    private val gameView = find(GameView::class)

    override val root = tabpane {
        gameTabPane = this
        tab {content = frontView.root}
        tab {
            gameTab = this
            content = gameView.root
        }
    }
}