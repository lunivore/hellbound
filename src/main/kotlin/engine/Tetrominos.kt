package com.lunivore.hellbound.engine

import com.lunivore.hellbound.model.Position
import com.lunivore.hellbound.model.Segment

private fun rotation(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int, x4: Int, y4: Int): TetrominoRotation {
    return TetrominoRotation(
        listOf(
            p(x1, y1),
            p(x2, y2),
            p(x3, y3),
            p(x4, y4)
        )
    )
}

private fun p(x: Int, y: Int): Segment { return Segment(x, y)
}

enum class TetrominoType(val tetrominoRotations: List<TetrominoRotation>, val ascii: Char) : List<TetrominoRotation> by tetrominoRotations {

    O(
        listOf(
            rotation(0, 0, 1, 0, 1, 1, 0, 1),
            rotation(0, 1, 0, 0, 1, 0, 1, 1),
            rotation(1, 1, 0, 1, 0, 0, 1, 0),
            rotation(1, 0, 1, 1, 0, 1, 0, 0)
        ),
        'O'){},
    T(
        listOf(
            rotation(-1, 0, 0, 0, 1, 0, 0, 1),
            rotation(-1, 2, -1, 1, -1, 0, 0, 1),
            rotation(1, 1, 0, 1, -1, 1, 0, 0),
            rotation(0, 0, 0, 1, 0, 2, -1, 1)
        ),
        'T'),

    I(
        listOf(
            rotation(0, 0, 0, 1, 0, 2, 0, 3),
            rotation(-1, 1, 0, 1, 1, 1, 2, 1),
            rotation(0, 3, 0, 2, 0, 1, 0, 0),
            rotation(2, 1, 1, 1, 0, 1, -1, 1)
        ),
        'I'),

    J(
        listOf(
            rotation(0, 0, 0, 1, 0, 2, -1, 2),
            rotation(-1, 0, -1, 1, 0, 1, 1, 1),
            rotation(-1, 2, -1, 1, -1, 0, 0, 0),
            rotation(-1, 0, 0, 0, 1, 0, 1, 1)
        ),
        'J'),
    L(
        listOf(
            rotation(-1, 0, -1, 1, -1, 2, 0, 2),
            rotation(-1, 1, 0, 1, 1, 1, 1, 0),
            rotation(0, 2, 0, 1, 0, 0, -1, 0),
            rotation(1, 0, 0, 0, -1, 0, -1, 1)
        ),
        'L'),
    Z(
        listOf(
            rotation(-1, 0, 0, 0, 0, 1, 1, 1),
            rotation(0, 0, 0, 1, -1, 1, -1, 2),
            rotation(1, 1, 0, 1, 0, 0, -1, 0),
            rotation(-1, 2, -1, 1, 0, 1, 0, 0)
        ),
        'Z'),
    S(
        listOf(
            rotation(-1, 1, 0, 1, 0, 0, 1, 0),
            rotation(-1, 0, -1, 1, 0, 1, 0, 2),
            rotation(1, 0, 0, 0, 0, 1, -1, 1),
            rotation(0, 2, 0, 1, -1, 1, -1, 0)
        ),
        'S');
}

data class TetrominoRotation(val segments : List<Segment>) : List<Segment> by segments {
    fun movedTo(center: Position): List<Segment> = map {
        it.movedRight(center.col).movedDown(center.row)
    }

}


data class Tetromino(val type : TetrominoType, val center : Position, val rotation : Int = 0) :
    List<Segment> by type[rotation].movedTo(center) {

    fun movedRight(): Tetromino = copy(center = center.right)
    fun movedLeft(): Tetromino = copy(center = center.left)
    fun movedDown(): Tetromino = copy(center = center.down)
    fun turnedClockwise(): Tetromino = copy(rotation = (rotation + 3)%4)
    fun turnedWiddershins(): Tetromino = copy(rotation = (rotation + 1)%4)
}