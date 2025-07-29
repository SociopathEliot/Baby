package be.buithg.etghaifgte.domain.repository

import be.buithg.etghaifgte.domain.models.Match

interface MatchRepository {
    suspend fun getScoreboard(
        sport: String,
        league: String,
        date: String? = null,
        limit: Int = 100
    ): List<Match>
}

