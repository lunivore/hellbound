package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.app.GridViewModel
import tornadofx.*

class GameView : View() {

    private val gridVM = GridViewModel()

    override val root = borderpane() {
        id = "gameView"
        top {
            hbox {
                label {
                    text = "SCORE:"
                }
            }
        }
        center {
            stackpane {
                group {
                    id = "gameGrid"
                    bindChildren(gridVM.squares) {
                        rectangle(it.x, it.y, it.scale, it.scale) {
                            fill = it.color
                        }
                    }
                }
            }
        }
    }


}
