package be.buithg.etghaifgte.domain.repository

import be.buithg.etghaifgte.domain.models.Match

interface MatchRepository {
    suspend fun getMatches(
        dates: List<java.time.LocalDate>,
        limit: Int = 100
    ): List<Match>
}

