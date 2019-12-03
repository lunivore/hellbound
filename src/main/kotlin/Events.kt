package com.lunivore.hellbound

import com.lunivore.hellbound.model.*
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
    val gameOverNotification = EventSource<HighScore>()
    val showWelcomeRequest = EventSource<Any>()
    val showWelcomeNotification = EventSource<Any>()
    val scoreChangedNotification = EventSource<Int>()
    val highScoreAchievedNotification = EventSource<HighScore>()
    val highScoreRecordRequest = EventSource<RecordedHighScore>()
    val highScoresChangedNotification = EventSource<List<RecordedHighScore>>()

            init {
        gridChangedNotification.subscribe { logger.info("Notification: Grid changed") }
        gamePlayingNotification.subscribe { logger.info("Notification: Game playing") }
        gameReadyRequest.subscribe { logger.info("Request: Game ready") }
        gameReadyNotification.subscribe { logger.info("Notification: Game ready") }
        playerMoveRequest.subscribe { logger.info("Request: Player move - ${it.name}") }
        heartbeatNotification.subscribe { logger.info("Notification: Heartbeat") }
        gameOverNotification.subscribe { logger.info("Notification: Game over") }
        showWelcomeRequest.subscribe { logger.info("Request: Return to Welcome screen") }
        showWelcomeNotification.subscribe { logger.info("Notification: Returning to Welcome screen") }
        scoreChangedNotification.subscribe { logger.info("Score: $it") }
    }
}
