package com.lunivore.hellbound.app

import javafx.scene.Parent
import tornadofx.View
import tornadofx.borderpane
import tornadofx.hbox

class MainView : View() {

    init {
        title = "Hellbound!"
    }

    override val root = hbox {
            minWidth = 400.0
            minHeight = 600.0
        }

}
