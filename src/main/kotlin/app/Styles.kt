package com.lunivore.hellbound.app

import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val gridSquare by cssclass()
    }

    init {
        gridSquare {
            padding = box(10.px)
            spacing = 10.px
        }
    }
}