package com.lunivore.hellbound.engine.glyph

data class Segment(val x: Int, val y: Int) {

    fun movedDown(): Segment {
        return Segment(x, y + 1)
    }

    fun movedDown(offset: Int): Segment {
        return Segment(x, y + offset)
    }

    fun movedLeft(): Segment {
        return Segment(x - 1, y)
    }

    fun movedRight(): Segment {
        return movedRight(1)
    }

    fun movedRight(offset: Int): Segment {
        return Segment(x + offset, y)
    }

    fun describe(): String {
        return "($x,$y)"
    }
}