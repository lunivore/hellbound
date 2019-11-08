package com.lunivore.hellbound

import com.lunivore.hellbound.engine.glyph.Segments
import javafx.scene.input.KeyCode
import org.apache.logging.log4j.LogManager
import org.reactfx.EventSource

class Events {
    val logger = LogManager.getLogger()


    val gridChangedNotification = EventSource<List<Segments>>()

    val gamePlayingNotification = EventSource<Any>()
    val gameReadyRequest = EventSource<Any>()
    val gameReadyNotification = EventSource<Any>()
    val keyPressNotification = EventSource<KeyCode>()

    init {
        gridChangedNotification.subscribe { logger.info("Notification: Grid changed") }
        gamePlayingNotification.subscribe { logger.info("Notification: Game playing") }
        gameReadyRequest.subscribe { logger.info("Request: Game ready") }
        gameReadyNotification.subscribe { logger.info("Notification: Game ready") }
        keyPressNotification.subscribe { logger.info("Notification: Key pressed - ${it.name}") }
    }
}
