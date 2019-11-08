package com.lunivore.hellbound.engine

import com.lunivore.hellbound.model.PlayerMove
import org.apache.logging.log4j.LogManager

interface State {
    fun getReady()
    fun move(playerMove: PlayerMove)
}

abstract class DefaultState() : State {
    companion object {
        val logger = LogManager.getLogger()
    }


    override fun getReady() {}
    override fun move(playerMove: PlayerMove) {}
}


