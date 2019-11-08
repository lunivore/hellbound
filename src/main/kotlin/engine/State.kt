package com.lunivore.hellbound.engine

import javafx.scene.input.KeyCode
import org.apache.logging.log4j.LogManager

interface State {
    fun getReady()
    fun keyPress(keyCode: KeyCode)
}

abstract class DefaultState() : State {
    companion object {
        val logger = LogManager.getLogger()
    }


    override fun getReady() {}
    override fun keyPress(keyCode: KeyCode) {}
}


