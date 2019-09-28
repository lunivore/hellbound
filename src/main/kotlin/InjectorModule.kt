package com.lunivore.hellbound

import com.google.inject.AbstractModule

class InjectorModule : AbstractModule() {
    override fun configure() {
        val events = Events()
        bind(Events::class.java).toInstance(events)
    }

}
