package com.lunivore.hellbound

import com.lunivore.hellbound.app.MainView
import javafx.application.Application
import tornadofx.App

class Hellbound: App(MainView::class) {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(Hellbound::class.java, *args)
        }
    }
}