package com.lunivore.hellbound

import com.google.inject.AbstractModule
import com.lunivore.hellbound.app.KeycodeTranslator
import com.lunivore.hellbound.engine.Controller
import com.lunivore.hellbound.engine.GameFactory
import com.lunivore.hellbound.engine.SinglePlayerGame
import com.lunivore.hellbound.model.GameSize

class InjectorModule(val seed : Long = System.currentTimeMillis()) : AbstractModule() {
    companion object {
        val logger = org.apache.logging.log4j.LogManager.getLogger()
    }

    override fun configure() {
        logger.info("Configuring Injector")
        val events = Events()
        val gameSize = GameSize()
        val keycodeTranslator = KeycodeTranslator()
        val gameFactory = object : GameFactory {
            override fun create(): SinglePlayerGame =
                SinglePlayerGame(events, gameSize, seed)
        }

        bind(Events::class.java).toInstance(events)
        bind(GameSize::class.java).toInstance(gameSize)
        bind(KeycodeTranslator::class.java).toInstance(keycodeTranslator)

        // Don't delete this unused instance! Or the garbage collector
        // will delete the engine too!
        val controller = Controller(events, gameFactory)
    }
}
