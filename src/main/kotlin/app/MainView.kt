package com.lunivore.hellbound.app

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.paint.Color
import tornadofx.*


class MainView : View() {

    private val gridVM = GridViewModel()

    init {
        title = "Hellbound!"
    }

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
                            id = "newGameButton"
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
    }
}

class   GridViewModel {
    private val gridHeight: Int = 20
    private val gridWidth: Int = 10
    private val gridScale: Double = 40.0
    val squares: ObservableList<Square> = FXCollections.observableArrayList()

    init {
        for (coli in 0..gridWidth) {
            for (rowi in 0..gridHeight) {
                squares.add(Square(coli, rowi, gridScale, Color.BLACK))
            }
        }
    }
}

data class Square(val col : Int, val row : Int, val scale : Double, val color : Color) {
    val x = col * scale
    val y = row * scale
}
