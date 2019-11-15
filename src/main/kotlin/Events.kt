package com.lunivore.hellbound

import com.lunivore.hellbound.model.GameSize
import com.lunivore.hellbound.model.PlayerMove
import com.lunivore.hellbound.model.Segment
import org.apache.logging.log4j.LogManager
import org.reactfx.EventSource

class Events {

    val logger = LogManager.getLogger()


    val gridChangedNotification = EventSource<List<Segment>>()

    val gamePlayingNotification = EventSource<Any>()
    val gameReadyRequest = EventSource<GameSize>()
    val gameReadyNotification = EventSource<GameSize>()
    val playerMoveRequest = EventSource<PlayerMove>()
    val heartbeatNotification = EventSource<Any>()
    val gameOverNotification = EventSource<Any>()
    val showWelcomeNotification = EventSource<Any>()

    init {
        gridChangedNotification.subscribe { logger.info("Notification: Grid changed") }
        gamePlayingNotification.subscribe { logger.info("Notification: Game playing") }
        gameReadyRequest.subscribe { logger.info("Request: Game ready") }
        gameReadyNotification.subscribe { logger.info("Notification: Game ready") }
        playerMoveRequest.subscribe { logger.info("Request: Player move - ${it.name}") }
        heartbeatNotification.subscribe { logger.info("Notification: Heartbeat") }
        gameOverNotification.subscribe { logger.info("Notification: Game over") }
        showWelcomeNotification.subscribe { logger.info("Notification: Returning to Welcome screen") }

    }
}
