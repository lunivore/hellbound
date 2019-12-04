import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.GameSize
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import tornadofx.*

class OverylayView : View(){
    val events : Events by di()
    val gameSize : GameSize by di()
    lateinit var messageLabel : Label
    lateinit var newGameButton : Button
    lateinit var welcomeScreenButton : Button

    companion object {
        val START_MESSAGE = "Press any key\nto start playing!"
        val GAME_OVER_MESSAGE = "Game over, man!\nGame over!"
    }

    init {
        events.gameReadyNotification.subscribe {
            messageLabel.text = START_MESSAGE
            newGameButton.hide()
            welcomeScreenButton.hide()

        }
        events.gameOverNotification.subscribe {
            messageLabel.text = GAME_OVER_MESSAGE
            newGameButton.show()
            welcomeScreenButton.show()
        }
    }

    override val root = borderpane {
        id = "overlayView"
        background = Background(BackgroundFill(Color(0.0, 0.0, 0.0, 0.5), CornerRadii.EMPTY, Insets.EMPTY))
        center {
            vbox {
                alignment = Pos.CENTER
                label {
                    messageLabel = this
                    textAlignment = TextAlignment.CENTER
                    text = START_MESSAGE
                    textFill = Color.WHITE
                }
                button {
                    newGameButton = this
                    text="New game"
                    action {
                        events.gameReadyRequest.push(gameSize)
                    }
                }
                button {
                    welcomeScreenButton = this
                    text="Return to Welcome Screen"
                    action {
                        events.showWelcomeRequest.push(Object())
                    }
                }
            }

        }
    }
}