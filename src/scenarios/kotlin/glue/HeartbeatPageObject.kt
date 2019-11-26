package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HeartbeatPageObject(val world: World) {

    fun beats() {
        Stirry.runOnPlatform { world.events.heartbeatNotification.push(Object()) }
        runBlocking { delay(100) }
    }

}
