package com.lunivore.hellbound.app

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class GameView : View() {

    private val gridVM = GridViewModel()

    override val root = borderpane() {
        id = "gameView"
        addClass(Styles.gameGrid)
        center {
            stackpane {
                group {
                    id = "gameGrid"
                    border = Border(BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))
                    bindChildren(gridVM.squares) {
                        rectangle(it.x, it.y, it.scale, it.scale) {
                            addClass(when(it.type?.toLowerCase()) {
                                'o' -> Styles.oSquare
                                'i' -> Styles.iSquare
                                't' -> Styles.tSquare
                                'z' -> Styles.zSquare
                                's' -> Styles.sSquare
                                'j' -> Styles.jSquare
                                'l' -> Styles.lSquare
                                else -> Styles.emptySquare
                            })
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
