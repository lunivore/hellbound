package com.lunivore.hellbound

import com.google.inject.AbstractModule
import com.lunivore.hellbound.app.KeycodeTranslator
import com.lunivore.hellbound.engine.*
import com.lunivore.hellbound.model.GameSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlin.coroutines.CoroutineContext

class InjectorModule(
    val seed: Long = 42,//System.currentTimeMillis(),
    val events: Events = Events(),
    val heartbeat: Heartbeat = AcceleratingHeartbeat(events, 1000, 1, createCoroutineScope())
) : AbstractModule() {
    companion object {
        val logger = org.apache.logging.log4j.LogManager.getLogger()
    }

    override fun configure() {
        logger.info("Configuring Injector")
        val gameSize = GameSize()
        val keycodeTranslator = KeycodeTranslator()
        val gameFactory = object : GameFactory {
            override fun create(): SinglePlayerGame =
                SinglePlayerGame(events, gameSize, seed)
        }

        bind(Events::class.java).toInstance(events)
        bind(GameSize::class.java).toInstance(gameSize)
        bind(KeycodeTranslator::class.java).toInstance(keycodeTranslator)

        // Don't delete these unused instances! Or the garbage collector
        // will delete the engine too!
        val controller = Controller(events, gameFactory)
        val heartbeat = heartbeat
    }
}

fun createCoroutineScope(): CoroutineScope = object : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.JavaFx
}
