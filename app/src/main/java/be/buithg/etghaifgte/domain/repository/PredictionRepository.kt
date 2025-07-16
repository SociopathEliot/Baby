package be.buithg.etghaifgte.domain.repository

import be.buithg.etghaifgte.data.local.entity.PredictionEntity

interface PredictionRepository {
    suspend fun addPrediction(prediction: PredictionEntity)
    suspend fun getPredictions(): List<PredictionEntity>
}
