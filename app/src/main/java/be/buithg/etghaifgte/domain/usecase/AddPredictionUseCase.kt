package be.buithg.etghaifgte.domain.usecase

import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.repository.PredictionRepository
import javax.inject.Inject

class AddPredictionUseCase @Inject constructor(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(prediction: PredictionEntity) {
        val existing = repository.getPrediction(prediction.teamA, prediction.teamB, prediction.dateTime)
        val entity = if (existing != null) {
            prediction.copy(id = existing.id)
        } else {
            prediction
        }
        repository.addPrediction(entity)
    }
}
