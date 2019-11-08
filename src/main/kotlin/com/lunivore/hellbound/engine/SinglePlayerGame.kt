package com.lunivore.hellbound.com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.GameSize
import com.lunivore.hellbound.engine.glyph.Segment
import com.lunivore.hellbound.engine.glyph.TetrominoRotations
import javafx.scene.input.KeyCode
import java.util.*

interface Game {
    fun startPlaying()
    fun keyPressed(code: KeyCode)
}

class SinglePlayerGame(
    val events: Events,
    gameSize: GameSize,
    seed: Long = System.currentTimeMillis()
) : Game {
    override fun keyPressed(code: KeyCode) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val random = Random(seed)
    val tetromino = TetrominoRotations.values()[random.nextInt(TetrominoRotations.values().size)]
    val rotation = 0
    val translation = Segment(gameSize.gridWidth / 2, 0)

    override fun startPlaying() {
        events.gridChangedNotification.push(listOf(tetromino[rotation].centerOn(translation)))
    }


}
