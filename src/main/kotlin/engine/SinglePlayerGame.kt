package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.*
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

    private var score: Int = 0
    private var junk = listOf<Segment>()

    private val random = Random(seed)
    private var nextType = TetrominoType.values()[random.nextInt(TetrominoType.values().size)]
    private lateinit var tetromino : Tetromino
    private val translation = Position(gameSize.cols / 2, 0)

    override fun heartbeat() {
        var candidateTetromino = tetromino.movedDown()
        if (candidateTetromino.any { it.isOutOfBounds(gameSize) || junk.collidesWith(it) }) {
            score = score + 10
            junk = junk.plus(tetromino)
            checkForNewLine()
            nextTetromino()
            checkForGameOver()
        } else {
            move(PlayerMove.DOWN)
        }
    }

    private fun checkForGameOver() {
        if (junk.any { tetromino.collidesWith(it) }) {
            events.gameOverNotification.push(HighScore(score))
        }
    }

    private fun checkForNewLine() {
        val junkLines = junk.groupBy { it.position.row }.values
        val rowsToDelete : List<Int> = junkLines.filter { it.size > gameSize.cols }.map { it.first().row }.sorted()
        for(line in rowsToDelete) {
            score = score + 100
            val aboveLine = junk.filter { it.position.row < line } // Remembering 0 is at the top
            val belowLine = junk.filter { it.position.row > line }
            junk = aboveLine.map {it.movedDown()}.plus(belowLine)
        }
    }

    override fun startPlaying() {
        score = 0
        junk = listOf()
        tetromino = Tetromino(nextType, translation)
        events.gridChangedNotification.push(tetromino)
        events.scoreChangedNotification.push(score)
    }

    private fun nextTetromino() {
        nextType = TetrominoType.values()[random.nextInt(TetrominoType.values().size)]
        tetromino = Tetromino(nextType, translation)
        events.gridChangedNotification.push(tetromino.plus(junk))
        events.scoreChangedNotification.push(score)
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
        if (candidateTetromino.none { it.isOutOfBounds(gameSize)  || junk.collidesWith(it) }) {
            tetromino = candidateTetromino
            events.gridChangedNotification.push(tetromino.plus(junk))
        }
    }

    private fun drop(): Tetromino {
        var currentTetromino = tetromino
        var candidateTetromino = currentTetromino.movedDown()
        while (candidateTetromino.none { it.isOutOfBounds(gameSize) || junk.collidesWith(it) }) {
            currentTetromino = candidateTetromino
            candidateTetromino = candidateTetromino.movedDown()
        }
        return currentTetromino
    }
}
