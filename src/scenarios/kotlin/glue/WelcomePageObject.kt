package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import javafx.scene.control.Label

class WelcomePageObject(world: World) {
    fun should_display_it() {
        val scores = Stirry.findInRoot<Label> { it.id == "highScoresLabel" }.value
        assertThat(scores.text, containsSubstring("Luni"))
    }

}
