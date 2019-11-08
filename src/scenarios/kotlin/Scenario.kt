package com.lunivore.hellbound

import com.lunivore.stirry.Stirry
import javafx.scene.Group
import javafx.scene.control.Button

abstract class Scenario {

    protected val nl = "\n"

    protected fun makeNewGameReady(): Group {
        val button = Stirry.findInRoot<Button> { it.id == "newGameButton" }.value
        Stirry.runOnPlatform {button.fire()}
        val gameGrid = Stirry.findInRoot<Group> { it.id == "gameGrid" }.value
        return gameGrid
    }

}
