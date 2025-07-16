package be.buithg.etghaifgte.domain.models

import java.io.Serializable

data class CricketData(
    val apikey: String,
    val `data`: List<Data>,
    val info: Info,
    val status: String
): Serializable