package be.buithg.etghaifgte.data.repository

import be.buithg.etghaifgte.data.remote.ApiInterface
import be.buithg.etghaifgte.domain.models.Match
import be.buithg.etghaifgte.domain.models.toMatches
import be.buithg.etghaifgte.domain.repository.MatchRepository
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : MatchRepository {

    override suspend fun getScoreboard(
        sport: String,
        league: String,
        date: String?,
        limit: Int
    ): List<Match> {
        return api.getScoreboard(sport, league, date, limit).toMatches()
    }
}
