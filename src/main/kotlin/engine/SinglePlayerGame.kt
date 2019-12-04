package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.engine.glyph.Tetromino
import com.lunivore.hellbound.engine.glyph.TetrominoType
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.PlayerMove
import com.lunivore.hellbound.model.Position
import java.util.*

/**
 * TetrominoType is a 4-square shape which can be rotated.
 *
 * A Tetromino is an instance of a TetrominoType, rotated and centred on a particular position. It is made up of 4 Segments.
 */
interface Game {
    fun startPlaying()
    fun move(direction: PlayerMove)
}

class SinglePlayerGame(
    val events: Events,
    gameSize: GameSize,
    seed: Long = System.currentTimeMillis()
) : Game {


    private val random = Random(seed)
    private var nextType = TetrominoType.values()[random.nextInt(TetrominoType.values().size)]
    private lateinit var tetromino : Tetromino
    private val rotation = 0
    private val translation = Position(gameSize.cols / 2, 0)

    override fun startPlaying() {
        tetromino = Tetromino(nextType, translation)
        events.gridChangedNotification.push(listOf(tetromino))
    }

    override fun move(move: PlayerMove) {
        tetromino = when(move) {
            PlayerMove.RIGHT -> tetromino.movedRight()
            PlayerMove.LEFT -> tetromino.movedLeft()
            PlayerMove.DOWN -> tetromino.movedDown()
            PlayerMove.CLOCKWISE -> tetromino.turnedClockwise()
            PlayerMove.WIDDERSHINS -> tetromino.turnedWiddershins()
            PlayerMove.DROP -> TODO()
            PlayerMove.UNMAPPED -> tetromino
        }
        if(move != PlayerMove.UNMAPPED) events.gridChangedNotification.push(listOf(tetromino))
    }


}
