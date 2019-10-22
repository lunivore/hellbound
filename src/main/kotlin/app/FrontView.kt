package com.lunivore.hellbound.com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import javafx.geometry.Pos
import tornadofx.*

class FrontView : View(){
    val events : Events by di()

    override val root = borderpane {
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
