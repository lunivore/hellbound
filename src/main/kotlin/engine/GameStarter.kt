package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events

class GameStarter(events: Events) {
    init {
        events.gameStartRequest.subscribe {
            events.gameStartNotification.push(Object())
        }
    }
}
