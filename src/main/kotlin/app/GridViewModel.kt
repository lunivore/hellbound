package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.Position
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

    companion object {
        val TYPES_TO_COLOR = mapOf(
            'O' to Color.RED,
            'I' to Color.ORANGERED,
            'T' to Color.ORANGE,
            'J' to Color.YELLOW,
            'L' to Color.FIREBRICK,
            'Z' to Color.CRIMSON,
            'S' to Color.DARKRED
        )
    }

    init {
        initializeSquares(GameSize())
        events.gameReadyNotification.subscribe {
            initializeSquares(it)
        }

        events.gridChangedNotification.subscribe {
            val segments = it
            val allPositions = it.map { it.position }

            squares.replaceAll {
                val position = Position(it.col, it.row)
                val matchingSegment = segments.firstOrNull { it.position == position }
                val color = if (matchingSegment != null) TYPES_TO_COLOR[matchingSegment.type]!! else Color.BLACK
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