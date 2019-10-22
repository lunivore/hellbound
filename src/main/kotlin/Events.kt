package com.lunivore.hellbound

import org.reactfx.EventSource

class Events {
    val gameStartRequest = EventSource<Any>()
    val gameStartNotification = EventSource<Any>()
}
