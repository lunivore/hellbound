package com.lunivore.hellbound.app

import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.StrokeType
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val gameGrid by cssclass()

        val emptySquare by cssclass()
        val oSquare by cssclass()
        val tSquare by cssclass()
        val iSquare by cssclass()
        val lSquare by cssclass()
        val jSquare by cssclass()
        val sSquare by cssclass()
        val zSquare by cssclass()
    }

    init {
        gameGrid {
            backgroundColor += Color.BLACK
        }
        emptySquare {
            val stops = listOf(Stop(0.6, Color.BLACK), Stop(1.0, Color.DARKGRAY))
            val radial = RadialGradient(0.0, 0.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, stops)
            fill = radial
        }
        oSquare { toRoundedRectangle(Color.CRIMSON)}
        tSquare { toRoundedRectangle(Color.RED)}
        iSquare { toRoundedRectangle(Color.ORANGE)}
        lSquare { toRoundedRectangle(Color.ORANGERED)}
        jSquare { toRoundedRectangle(Color.DARKORANGE)}
        sSquare { toRoundedRectangle(Color.PALEVIOLETRED)}
        zSquare { toRoundedRectangle(Color.FIREBRICK)}
    }

    private fun CssSelectionBlock.toRoundedRectangle(color: Color?) {
        val stops = listOf(Stop(0.3, color), Stop(1.0, Color.WHITE))
        val radial = RadialGradient(0.0, 0.0, 0.5, 0.5, 1.0, true, CycleMethod.NO_CYCLE, stops)
        fill = radial
        arcWidth = Dimension(20.0, Dimension.LinearUnits.px)
        arcHeight = Dimension(20.0, Dimension.LinearUnits.px)
        stroke = Color.BLACK
        strokeType = StrokeType.INSIDE
    }
}