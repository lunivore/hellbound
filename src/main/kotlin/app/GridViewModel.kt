package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.GameSize
import com.lunivore.hellbound.engine.glyph.Segment
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.paint.Color
import tornadofx.*

class   GridViewModel : ViewModel() {
    val gs : GameSize by di()
    val events : Events by di()

    val squares: ObservableList<Square> =
        FXCollections.observableArrayList()

    init {
        for (rowi in 0..gs.gridHeight) {
            for (coli in 0..gs.gridWidth) {
                squares.add(Square(coli, rowi, gs.gridScale, Color.BLACK))
            }
        }
        events.gridChangedNotification.subscribe {
            val allSegments = it.flatMap { it.segmentsByRotation }

            squares.replaceAll {
                val color = if (allSegments.contains(Segment(it.col, it.row))) Color.RED else Color.BLACK
                Square(it.col, it.row, gs.gridScale, color)
            }
        }
    }
}