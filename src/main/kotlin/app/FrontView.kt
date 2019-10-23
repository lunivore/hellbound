package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class FrontView : View(){
    val events : Events by di()

    override val root = borderpane {
        useMaxWidth = true
        useMaxHeight = true
        background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
        keyboard {
            addEventHandler(KeyEvent.KEY_PRESSED) { println(it.code) }
        }
        center {
            vbox {
                alignment = Pos.CENTER
                label("Welcome to Hellbound!")
                button("New Game") {
                    id = "newGameButton"
                    action { events.gameStartRequest.push(Object())}
                }
            }
        }
    }
}
