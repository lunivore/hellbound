package com.lunivore.hellbound

import com.google.inject.Guice
import com.google.inject.Injector
import com.lunivore.hellbound.app.MainView
import javafx.application.Application
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.reloadStylesheetsOnFocus
import kotlin.reflect.KClass

class Hellbound(val guice : Injector): App(MainView::class) {

    constructor() : this(Guice.createInjector(InjectorModule()))

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