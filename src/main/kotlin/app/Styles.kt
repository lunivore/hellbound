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

        val pane by csselement("BorderPane")
        val dialog by csselement("DialogPane")
        val label by csselement("Label")
        val textfield by csselement("TextField")
        val button by csselement("Button")
        val highScore by cssid("highScoresLabel")
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

        pane {
            backgroundColor = multi(Color.BLACK)
            fill = Color.BLACK
        }
        dialog {
            backgroundColor = multi(Color.BLACK)
            borderColor = multi(box(Color.RED), box(Color.ORANGE), box(Color.YELLOW))
            borderInsets = multi(box(30.0.px), box(20.0.px), box(10.0.px))
            borderRadius = multi(box(10.0.px))
            headerPanel {
                backgroundColor = multi(Color.BLACK)
            }
            content {
                backgroundColor = multi(Color.BLACK)
            }
            label {
                backgroundColor = multi(Color.BLACK)
                fontSize = Dimension(24.0, Dimension.LinearUnits.px)
                textFill = Color.WHITE
                padding = box(20.0.px)
            }
        }

        button {
            backgroundColor = multi(Color.WHITE, Color.LIGHTGRAY, Color.DARKGRAY)
            fontSize = Dimension(36.0, Dimension.LinearUnits.px)
            backgroundInsets = multi(box(10.0.px))
            backgroundRadius = multi(box(10.0.px))
        }
        label {
            backgroundColor = multi(Color.BLACK)
            fontSize = Dimension(32.0, Dimension.LinearUnits.px)
            textFill = Color.WHITE
            padding = box(20.0.px)
        }
        highScore {
            borderColor = multi(box(Color.RED), box(Color.ORANGE), box(Color.YELLOW))
            borderInsets = multi(box(10.0.px), box(20.0.px), box(30.0.px))
            borderRadius = multi(box(20.0.px))
        }

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