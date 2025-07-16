package be.buithg.etghaifgte.domain.usecase

import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.repository.PredictionRepository
import javax.inject.Inject

class GetPredictionsUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(): List<PredictionEntity> {
        return repository.getPredictions()
    }
}
