package com.lunivore.hellbound

import com.google.inject.AbstractModule
import com.lunivore.hellbound.engine.GameStarter

class InjectorModule : AbstractModule() {
    override fun configure() {
        val events = Events()
        bind(Events::class.java).toInstance(events)

        val gameStarter = GameStarter(events)
    }

}
