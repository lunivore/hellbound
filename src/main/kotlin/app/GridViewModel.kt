package com.lunivore.hellbound.app

import com.lunivore.hellbound.GameSize
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.paint.Color
import tornadofx.*

class   GridViewModel : ViewModel() {
    val gs : GameSize by di()

    val squares: ObservableList<Square> =
        FXCollections.observableArrayList()

    init {
        for (rowi in 0..gs.gridHeight) {
            for (coli in 0..gs.gridWidth) {
                squares.add(Square(coli, rowi, gs.gridScale, Color.BLACK))
            }
        }
    }
}