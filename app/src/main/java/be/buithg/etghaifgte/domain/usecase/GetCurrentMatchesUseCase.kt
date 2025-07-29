package be.buithg.etghaifgte.domain.usecase

import be.buithg.etghaifgte.domain.models.Match
import be.buithg.etghaifgte.domain.repository.MatchRepository
import javax.inject.Inject

class GetCurrentMatchesUseCase @Inject constructor(
    private val repository: MatchRepository
) {
    suspend operator fun invoke(
        dates: List<java.time.LocalDate>,
        limit: Int = 100
    ): List<Match> {
        return repository.getMatches(dates, limit)
    }
}

