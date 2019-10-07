package com.lunivore.hellbound.app

import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*
import javax.swing.border.Border

class MainView : View() {

    init {
        title = "Hellbound!"
    }

    private val gridHeight: Int = 20
    private val gridWidth: Int = 10
    private val gridScale: Double = 40.0
    override val root = tabpane {
        tab {
            borderpane() {
                top {
                    label("Welcome to Hellbound!")
                }
            }
        }
        tab {
            borderpane() {
                top {
                    label {text = "SCORE:" }
                    label {}
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
