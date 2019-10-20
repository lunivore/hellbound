package com.lunivore.hellbound.app

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class MainView : View() {

    init {
        title = "Hellbound!"
    }

    private val gridHeight: Int = 20
    private val gridWidth: Int = 10
    private val gridScale: Double = 40.0

    private var gameTabPane : TabPane by singleAssign()
    private var gameTab : Tab by singleAssign()

    override val root = tabpane {
        gameTabPane = this
        tab {
            borderpane() {
                center {
                    vbox {
                        alignment = Pos.CENTER
                        label("Welcome to Hellbound!")
                        button("New Game") {
                            action {
                                gameTabPane.selectionModel.select(gameTab)
                            }
                        }
                    }
                }
            }
        }
        tab {
            gameTab = this
            borderpane() {
                top {
                    hbox {
                        label {
                            text = "SCORE:"
                        }
                    }
                }
                center {
                    pane {
                        val blackFill = BackgroundFill(Color.BLACK, CornerRadii(0.0), Insets(0.0))
                        background = Background(blackFill)
                        canvas(gridWidth * gridScale, gridHeight * gridScale) {
                            with(graphicsContext2D) {
                                stroke = Color.LIGHTGRAY
                                lineWidth = 2.0
                                for (coli in 0..gridWidth) strokeLine(
                                    coli * gridScale,
                                    0.0,
                                    coli * gridScale,
                                    gridHeight * gridScale
                                )
                                for (rowi in 0..gridHeight) strokeLine(
                                    0.0,
                                    rowi * gridScale,
                                    gridWidth * gridScale,
                                    rowi * gridScale
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
