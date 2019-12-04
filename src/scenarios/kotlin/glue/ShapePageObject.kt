package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane

class ShapePageObject(private val world: World) {
    fun is_dropped() {
        val gameView = Stirry.findInRoot<BorderPane> { it.id == "gameView" }.value
        pressKey(gameView, KeyCode.SPACE)
        Stirry.runOnPlatform { world.events.heartbeatNotification.push(Object()) }
    }
}