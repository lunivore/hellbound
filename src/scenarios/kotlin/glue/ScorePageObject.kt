package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.scene.control.Label

class ScorePageObject(world: World) {
    fun should_be(expectedScore: Int) {
        val label = Stirry.findInRoot<Label> { it.id == "scoreLabel" }.value
        assertThat(label.text, equalTo(expectedScore.toString()))
    }
}
