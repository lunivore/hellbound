package com.lunivore.hellbound.model

data class HighScore(val score : Int) {
    fun isHigherThanAny(scores: List<RecordedHighScore>) = scores.any { it.score < this.score }
}

data class RecordedHighScore(val score : Int, val name : String)
