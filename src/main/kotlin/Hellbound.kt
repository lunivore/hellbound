package com.lunivore.hellbound

import com.google.inject.Guice
import com.google.inject.Injector
import com.lunivore.hellbound.app.MainView
import com.lunivore.hellbound.app.Styles
import javafx.application.Application
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import tornadofx.*
import kotlin.reflect.KClass

class Hellbound(val guice : Injector): App(MainView::class, Styles::class, Scope()) {

    constructor() : this(Guice.createInjector(InjectorModule()))

    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        logger.info("Starting Hellbound")
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>) = guice.getInstance(type.java)
        }
        super.start(stage)
    }

    companion object {
        val logger = LogManager.getLogger()

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Hellbound::class.java, *args)
        }
    }
}