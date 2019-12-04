package com.lunivore.hellbound.engine

import com.lunivore.hellbound.model.HighScore

interface Referee {
    fun finalScoreWas(score: HighScore)
}
