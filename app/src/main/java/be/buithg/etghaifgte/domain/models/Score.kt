package be.buithg.etghaifgte.domain.models

import java.io.Serializable

data class Score(
    val inning: String,
    val o: Double,
    val r: Int,
    val w: Int
): Serializable

