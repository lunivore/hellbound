package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.engine.glyph.Segment
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.Scale
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.paint.Color
import tornadofx.ViewModel

class   GridViewModel : ViewModel() {

    val events : Events by di()
    val scale : Scale by di()
    val squares: ObservableList<Square> =
        FXCollections.observableArrayList()

    init {
        initializeSquares(GameSize())
        events.gameReadyNotification.subscribe {
            initializeSquares(it)
        }

        events.gridChangedNotification.subscribe {
            val allSegments = it.flatMap { it }

            squares.replaceAll {
                val color = if (allSegments.contains(Segment(it.col, it.row))) Color.RED else Color.BLACK
                Square(it.col, it.row, scale.value, color)
            }
        }
    }

    private fun initializeSquares(it: GameSize) {
        squares.clear()
        for (rowi in 0..it.rows) {
            for (coli in 0..it.cols) {
                squares.add(Square(coli, rowi, scale.value, Color.BLACK))
            }
        }
    }
}