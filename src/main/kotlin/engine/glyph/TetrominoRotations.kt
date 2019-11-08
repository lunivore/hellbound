package com.lunivore.hellbound.engine.glyph

private fun rotation(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int, x4: Int, y4: Int): Segments {
    return Segments(listOf(p(x1, y1), p(x2, y2), p(x3, y3), p(x4, y4)))
}

private fun p(x: Int, y: Int): Segment { return Segment(x, y) }

enum class TetrominoRotations(val segmentsByRotation: List<Segments>, val ascii: Char) : List<Segments> by segmentsByRotation {

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

    fun turnSegments(quarterTurnsToLeft: Int): Segments {
        return segmentsByRotation[quarterTurnsToLeft]
    }

    fun toAscii(): Char {
        return ascii
    }
}

// We could just use a list of segments, but the chances of getting the whole grid mixed up with
// glyphs, detritus or anything else seems non-zero. This way we can pass around different groups of segments
// and keep them completely separate.
data class Segments(val segmentsByRotation : List<Segment>) : List<Segment> by segmentsByRotation {
    fun centerOn(translation: Segment): Segments {
        return Segments(segmentsByRotation.map { it.movedRight(translation.x).movedDown(translation.y) })
    }
}
