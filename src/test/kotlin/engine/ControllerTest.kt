package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.PlayerMove
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.mockito.Mockito.*

class ControllerTest {

    @Test
    fun `should start on Welcome state`() {
        val controller = Controller(Events(), mock(GameFactory::class.java))
    }

    @Test
    fun `should move from Welcome to Ready on request`() {
        // Given a controller and some events to which we're subscribed
        val events = Events()
        var readyNotificationReceived = false
        events.gameReadyNotification.subscribe { readyNotificationReceived = true }
        val controller = Controller(events, mock(GameFactory::class.java))

        // When the controller receives a request to make the game ready
        events.gameReadyRequest.push(Object())

        // Then it should make the game ready and notify us
        assertThat(readyNotificationReceived, equalTo(true))
    }

    @Test
    fun `should move from Ready to Playing on keypress, and tell the game`() {
        // Given a controller and some events to which we're subscribed
        val events = Events()
        var playingNotificationReceived = false
        events.gamePlayingNotification.subscribe { playingNotificationReceived = true }

        // And a game that's ready and will be played
        val game = mock(Game::class.java)
        val controller = Controller(events, object : GameFactory { override fun create(): Game = game })
        events.gameReadyRequest.push(Object())

        // When the controller receives any keycode
        events.playerMoveRequest.push(PlayerMove.UNMAPPED)

        // Then it should start the game playing and notify us
        assertThat(playingNotificationReceived, equalTo(true))
        verify(game).startPlaying()
    }

    @Test
    fun `should start passing key presses to the game once it's in play`() {
        // Given a controller and some events to which we're subscribed
        val events = Events()
        var playingNotificationReceived = false
        events.gamePlayingNotification.subscribe { playingNotificationReceived = true }

        // And a game that's in play
        val game = mock(Game::class.java)
        val controller = Controller(events, object : GameFactory { override fun create(): Game = game })
        events.gameReadyRequest.push(Object())
        events.playerMoveRequest.push(PlayerMove.UNMAPPED) // First keypress starts the game playing

        // When the controller receives another keypress
        events.playerMoveRequest.push(PlayerMove.DROP)

        // Then it should pass it on to the game
        verify(game, times(1)).move(PlayerMove.DROP)
    }

    @Test
    fun `should not respond to requests which are for the wrong state of play`() {
        // Given some events to which we're subscribed
        val events = Events()
        var readyNotificationReceived = false
        var playingNotificationReceived = false
        events.gameReadyNotification.subscribe { readyNotificationReceived = true }
        events.gamePlayingNotification.subscribe { playingNotificationReceived = true }

        // And a controller which will start a game eventually
        val game = mock(Game::class.java)
        val controller = Controller(events, object : GameFactory { override fun create(): Game = game })

        // When we send a keycode and it's still on the welcome screen
        events.playerMoveRequest.push(PlayerMove.UNMAPPED)

        // Then the game should not be started
        verify(game, never()).startPlaying()

        // And we shouldn't have received any notification of readiness or playing
        assertThat(readyNotificationReceived, equalTo(false))
        assertThat(playingNotificationReceived, equalTo(false))
    }
}