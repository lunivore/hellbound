package com.lunivore.hellbound.app

import javafx.scene.layout.HBox
import tornadofx.View

class MainView : View() {

    init {
        title = "Hellbound!"
    }

    override val root = HBox()
}

