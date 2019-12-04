package com.lunivore.hellbound.app

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.paint.Color

class   GridViewModel {
    private val gridHeight: Int = 20
    private val gridWidth: Int = 10
    private val gridScale: Double = 40.0
    val squares: ObservableList<Square> =
        FXCollections.observableArrayList()

    init {
        for (coli in 0..gridWidth) {
            for (rowi in 0..gridHeight) {
                squares.add(Square(coli, rowi, gridScale, Color.BLACK))
            }
        }
    }
}