package be.buithg.etghaifgte.data.repository

import be.buithg.etghaifgte.data.remote.ApiInterface
import be.buithg.etghaifgte.domain.models.Match
import be.buithg.etghaifgte.domain.models.Event
import be.buithg.etghaifgte.domain.models.toMatch

import be.buithg.etghaifgte.domain.repository.MatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : MatchRepository {

    // List of sports and leagues to fetch
    private val leaguePaths = listOf(
        "football"   to "nfl",
        "basketball" to "nba",
        "baseball"   to "mlb",
        "hockey"     to "nhl",
        "soccer"     to "eng.1",
        "soccer"     to "fra.1"
    )

    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    override suspend fun getMatches(dates: List<LocalDate>, limit: Int): List<Match> = coroutineScope {
        // Create async requests for each date/league pair
        val deferreds = dates.flatMap { date ->
            leaguePaths.map { (sport, league) ->
                async(Dispatchers.IO) {
                    val dateStr = date.format(formatter)
                    val resp = runCatching {
                        api.getScoreboard(sport, league, dates = dateStr, limit = limit)
                    }.getOrNull()

                    val events = resp?.events
                        ?.mapNotNull { it.toMatch(league) }
                        .orEmpty()

                    if (events.isNotEmpty()) {
                        events
                    } else {
                        api.getScoreboard(sport, league, dates = null, limit = limit)
                            .events
                            ?.mapNotNull { it.toMatch(league) }
                            .orEmpty()
                    }
                }
            }
        }

        val allMatches = deferreds.map { it.await() }.flatten()

        allMatches.sortedBy { runCatching { Instant.parse(it.dateTimeGMT) }.getOrNull() }

    }
}
