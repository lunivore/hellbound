package com.lunivore.hellbound

import com.lunivore.hellbound.glue.Scenario
import org.junit.Test

class CanKeepScore : Scenario() {

    @Test
    fun `Should give 10 points for dropping a shape`() {
        Given_a_game.thats_just_started()
        When_the_shape.is_dropped()
        And_the_next_shape.is_dropped()
        Then_the_score.should_be(20)
    }

    @Test
    fun `Should give 100 points for a line`() {
        Given_a_game.with_existing_play("""
            EEDDDD h
            EDDD h
            EDDDDD h
            D h
            E h
            QAAA h
            """)
            .and_the_current_shape("QAAAA")

        When_the_shape.is_dropped()

        Then_the_score.should_be(170) // 100 + 7 shapes dropped
    }

    @Test
    fun `Keeps a list of player high-scores`() {
        Given_a_game.which_is_above_the_high_scores()
            .and_with_a_play().that_is_about_to_lose()
        When_the_shape.is_dropped()
        Then_the_game.should_prompt_for_the_player_name()
        And_the_welcome_screen.should_display_it()
    }


}