package com.lunivore.hellbound.engine

import com.lunivore.hellbound.Events
import com.lunivore.hellbound.model.HighScore
import com.lunivore.hellbound.model.RecordedHighScore

class HighScoreReferee(val events : Events) : Referee {
    companion object {
        var scores = listOf(
                RecordedHighScore(10000, "Awesome"),
                RecordedHighScore(5000, "Super"),
                RecordedHighScore(1000, "Wow"),
                RecordedHighScore(500, "Yeah"),
                RecordedHighScore(0, "OK")
        )
    }

    init {
        events.highScoreRecordRequest.subscribe {
            scores = scores.plus(it).sortedByDescending {it.score }.take(5)
            events.highScoresChangedNotification.push(scores)
        }
        events.highScoresChangedNotification.push(scores)
    }

    override fun finalScoreWas(score: HighScore) {
        if (score.isHigherThanAny(scores)) {
            events.highScoreAchievedNotification.push(score)
        }
    }

}
