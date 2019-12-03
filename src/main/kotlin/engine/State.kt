package com.lunivore.hellbound.engine

import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.HighScore
import com.lunivore.hellbound.model.PlayerMove
import org.apache.logging.log4j.LogManager

interface State {
    fun getReady(size: GameSize)
    fun move(playerMove: PlayerMove)
    fun heartbeat()
    fun welcome()
    fun gameOver(finalScore: HighScore)
}

abstract class DefaultState() : State {
    companion object {
        val logger = LogManager.getLogger()
    }

    override fun getReady(size: GameSize) {}
    override fun move(playerMove: PlayerMove) {}
    override fun heartbeat() {}
    override fun welcome() {}
    override fun gameOver(finalScore: HighScore) {}
}


