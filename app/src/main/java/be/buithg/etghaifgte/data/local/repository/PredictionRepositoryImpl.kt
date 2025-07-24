package be.buithg.etghaifgte.data.local.repository

import be.buithg.etghaifgte.data.local.dao.PredictionDao
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.repository.PredictionRepository
import javax.inject.Inject

class PredictionRepositoryImpl @Inject constructor(
    private val dao: PredictionDao
) : PredictionRepository {
    override suspend fun addPrediction(prediction: PredictionEntity) {
        dao.insert(prediction)
    }

    override suspend fun getPredictions(): List<PredictionEntity> {
        return dao.getAll()
    }

    override suspend fun getPrediction(teamA: String, teamB: String, dateTime: String): PredictionEntity? {
        return dao.getByMatch(teamA, teamB, dateTime)
    }
}
