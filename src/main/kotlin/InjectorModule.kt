package com.lunivore.hellbound

import com.google.inject.AbstractModule
import com.lunivore.hellbound.engine.GameStarter

class InjectorModule : AbstractModule() {
    override fun configure() {
        val events = Events()
        bind(Events::class.java).toInstance(events)
        bind(GameSize::class.java).toInstance(GameSize())

        val gameStarter = GameStarter(events)
    }
}

data class GameSize(val gridHeight: Int = 20, val gridWidth: Int = 10,val gridScale: Double = 40.0)
