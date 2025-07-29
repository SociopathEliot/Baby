package be.buithg.etghaifgte.domain.usecase

import be.buithg.etghaifgte.domain.models.Match
import be.buithg.etghaifgte.domain.repository.MatchRepository
import javax.inject.Inject

class GetCurrentMatchesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(
        sport: String,
        league: String,
        date: String? = null,
        limit: Int = 100
    ): List<Match> {
        return repository.getScoreboard(sport, league, date, limit)
    }
}

