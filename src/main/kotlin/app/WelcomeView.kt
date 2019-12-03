package com.lunivore.hellbound.app

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.RecordedHighScore
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TextInputDialog
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import tornadofx.*

class WelcomeView : View(){

    class HighScoreVM(events: Events, stage: WelcomeView) {
        val text: SimpleStringProperty = SimpleStringProperty("")

        init {
            events.highScoresChangedNotification.subscribe {
                val scoresAsString = it.map { "${it.name} ${it.score}" }.joinToString("\n")
                text.set(scoresAsString)
            }
            events.highScoreAchievedNotification.subscribe {(score) ->
                Platform.runLater {
                    val nameDialog = TextInputDialog("")
                    nameDialog.initStyle(StageStyle.UNDECORATED)
                    nameDialog.initOwner(stage.currentWindow)
                    nameDialog.graphic = null
                    nameDialog.headerText = "You got a high score!"
                    nameDialog.title = "Congratulations!"
                    nameDialog.contentText = "Please enter your name:"
                    nameDialog.showAndWait().ifPresent {
                        events.highScoreRecordRequest.push(RecordedHighScore(score, it))
                    }
                }
            }
        }
    }

    val events : Events by di()
    val highScores  = HighScoreVM(events, this)

    override fun onDock() {
        events.highScoreRecordRequest.push(RecordedHighScore(0, "")) // TODO: Replace this shoddy way of getting scores
        super.onDock()
    }

    override val root = borderpane {
        id = "welcomeView"
        useMaxWidth = true
        useMaxHeight = true
        background = Background(BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))
        center {
            vbox {
                alignment = Pos.CENTER
                label("Welcome to Hellbound!")

                label {
                    id = "highScoresLabel"
                    bind(highScores.text)
                }

                button("New Game") {
                    id = "newGameButton"
                    action { events.gameReadyRequest.push(GameSize())}
                }
            }
        }
    }
}
