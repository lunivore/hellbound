package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.PlayerMove
import com.lunivore.hellbound.model.Position
import com.lunivore.hellbound.model.Segment
import java.util.*

/**
 * TetrominoType is a 4-square shape which can be rotated.
 *
 * A Tetromino is an instance of a TetrominoType, rotated and centred on a particular position. It is made up of 4 Segments.
 */
interface Game {
    fun startPlaying()
    fun move(direction: PlayerMove)
    fun heartbeat()
}

class SinglePlayerGame(
    private val events: Events,
    private val gameSize: GameSize,
    private val seed: Long = System.currentTimeMillis()
) : Game {

    private var junk = listOf<Segment>()

    private val random = Random(seed)
    private var nextType = TetrominoType.values()[random.nextInt(TetrominoType.values().size)]
    private lateinit var tetromino : Tetromino
    private val translation = Position(gameSize.cols / 2, 0)

    override fun heartbeat() {
        var candidateTetromino = tetromino.movedDown()
        if (candidateTetromino.any { it.isOutOfBounds(gameSize) || junk.contains(it) }) {
            junk = junk.plus(tetromino)
            checkForNewLine()
            nextTetromino()
        } else {
            move(PlayerMove.DOWN)
        }
    }

    private fun checkForNewLine() {
        val junkLines = junk.groupBy { it.position.row }.values
        val rowsToDelete : List<Int> = junkLines.filter { it.size > gameSize.cols }.map { it.first().row }.sorted()
        for(line in rowsToDelete) {
            val aboveLine = junk.filter { it.position.row < line } // Remembering 0 is at the top
            val belowLine = junk.filter { it.position.row > line }
            junk = aboveLine.map {it.movedDown()}.plus(belowLine)
        }
    }

    override fun startPlaying() {
        tetromino = Tetromino(nextType, translation)
        events.gridChangedNotification.push(tetromino)
    }

    private fun nextTetromino() {
        nextType = TetrominoType.values()[random.nextInt(TetrominoType.values().size)]
        tetromino = Tetromino(nextType, translation)
        events.gridChangedNotification.push(tetromino.plus(junk))
    }

    override fun move(move: PlayerMove) {
        var candidateTetromino = when(move) {
            PlayerMove.RIGHT -> tetromino.movedRight()
            PlayerMove.LEFT -> tetromino.movedLeft()
            PlayerMove.DOWN -> tetromino.movedDown()
            PlayerMove.CLOCKWISE -> tetromino.turnedClockwise()
            PlayerMove.WIDDERSHINS -> tetromino.turnedWiddershins()
            PlayerMove.DROP -> drop()
            PlayerMove.UNMAPPED -> tetromino
        }
        if (candidateTetromino.none { it.isOutOfBounds(gameSize)  || junk.contains(it) }) {
            tetromino = candidateTetromino
            events.gridChangedNotification.push(tetromino.plus(junk))
        }
    }

    private fun moveDown(): Tetromino {
        var candidateTetromino = tetromino.movedDown()
        if (candidateTetromino.none { it.isOutOfBounds(gameSize)  || junk.contains(it) }) {
            candidateTetromino = candidateTetromino.movedDown()
        }
        return candidateTetromino
    }

    private fun drop(): Tetromino {
        var currentTetromino = tetromino
        var candidateTetromino = currentTetromino.movedDown()
        while (candidateTetromino.none { it.isOutOfBounds(gameSize) || junk.contains(it) }) {
            currentTetromino = candidateTetromino
            candidateTetromino = candidateTetromino.movedDown()
        }
        return currentTetromino
    }
}
