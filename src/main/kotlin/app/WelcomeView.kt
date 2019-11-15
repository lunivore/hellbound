package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*

class WelcomeView : View(){
    val events : Events by di()

    override val root = borderpane {
        id = "welcomeView"
        useMaxWidth = true
        useMaxHeight = true
        background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
        center {
            vbox {
                alignment = Pos.CENTER
                label("Welcome to Hellbound!")
                button("New Game") {
                    id = "newGameButton"
                    action { events.gameReadyRequest.push(GameSize())}
                }
            }
        }
    }
}
