package be.buithg.etghaifgte.domain.repository

import be.buithg.etghaifgte.domain.models.Data

interface MatchRepository {
    suspend fun getCurrentMatches(apiKey: String): List<Data>
}

