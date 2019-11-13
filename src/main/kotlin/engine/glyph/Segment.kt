package com.lunivore.hellbound.engine.glyph

import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.Position


/** A segment has both a position and a type, so that it can show up in different colours. **/
data class Segment(val position : Position) {

    constructor(col: Int, row: Int) : this(Position(col, row))
    private val col = position.col
    private val row = position.row

    fun movedDown(): Segment = copy(position.down)

    fun movedDown(offset: Int): Segment = Segment(col, row + offset)

    fun movedLeft(): Segment = copy(position.left)

    fun movedRight(): Segment = copy(position.right)

    fun movedRight(offset: Int): Segment = Segment(col + offset, row)

    fun describe(): String = "($col,$row)"

    fun isOutOfBounds(gameSize: GameSize): Boolean = position.isOutOfBounds(gameSize)
}