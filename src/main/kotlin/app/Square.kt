package com.lunivore.hellbound.app

import javafx.scene.paint.Color

data class Square(val col : Int, val row : Int, val scale : Double, val color : Color) {
    val x = col * scale
    val y = row * scale
}