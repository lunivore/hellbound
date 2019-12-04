package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.app.GridViewModel
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
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
                    border = Border(BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))
                    bindChildren(gridVM.squares) {
                        rectangle(it.x, it.y, it.scale, it.scale) {
                            if (it.isEmpty) {
                                val stops = listOf(Stop(0.6, it.color), Stop(1.0, Color.DARKGRAY))
                                val radial = RadialGradient(0.0, 0.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, stops)
                                fill = radial
                            } else {
                                val stops = listOf(Stop(0.3, it.color), Stop(1.0, Color.WHITE))
                                val radial = RadialGradient(0.0, 0.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, stops)
                                fill = radial
                                arcWidth = it.scale/2.0
                                arcHeight = it.scale/2.0
                                background = Background(BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY))
                            }
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
