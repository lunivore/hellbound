package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.app.GridViewModel
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class GameView : View() {

    private val gridVM = GridViewModel()

    override val root = borderpane() {
        id = "gameView"
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
        right {
            vbox {
                alignment = Pos.TOP_CENTER
                background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                label {
                    text = "SCORE"
                    font = Font.font("Arial", 20.0)
                    textFill = Color.WHITE
                }
                label {
                    alignment = Pos.CENTER
                    id="scoreLabel"
                    minWidth = 200.0
                    bind(gridVM.score)
                    font = Font.font("Arial", 20.0)
                    textFill = Color.WHITE
                }
            }
        }
    }


}
