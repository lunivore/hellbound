package com.lunivore.hellbound.model

data class Position(val col : Int, val row : Int) {
    val right: Position
        get() = copy(col + 1, row)
    val left: Position
        get() = copy(col - 1, row)
    val down: Position
        get() = copy(col, row + 1)
}

data class GameSize(val cols : Int = 10, val rows : Int = 20)

data class Scale(val value : Double = 40.0)