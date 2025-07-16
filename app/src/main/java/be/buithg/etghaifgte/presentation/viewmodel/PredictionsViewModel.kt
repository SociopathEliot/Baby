package be.buithg.etghaifgte.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.usecase.AddPredictionUseCase
import be.buithg.etghaifgte.domain.usecase.GetPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PredictionsViewModel @Inject constructor(
    private val addPredictionUseCase: AddPredictionUseCase,
    private val getPredictionsUseCase: GetPredictionsUseCase
) : ViewModel() {

    private val _predictions = MutableLiveData<List<PredictionEntity>>()
    val predictions: LiveData<List<PredictionEntity>> = _predictions

    private val _predictedCount = MutableLiveData<Int>()
    val predictedCount: LiveData<Int> = _predictedCount

    private val _upcomingCount = MutableLiveData<Int>()
    val upcomingCount: LiveData<Int> = _upcomingCount

    private val _wonCount = MutableLiveData<Int>()
    val wonCount: LiveData<Int> = _wonCount

    fun loadPredictions() {
        viewModelScope.launch {
            val list = getPredictionsUseCase()
            _predictions.value = list
            updateCounts(list)
        }
    }

    fun addPrediction(entity: PredictionEntity) {
        viewModelScope.launch {
            addPredictionUseCase(entity)
            loadPredictions()
        }
    }

    private fun updateCounts(list: List<PredictionEntity>) {
        _predictedCount.value = list.size
        _upcomingCount.value = list.count { it.upcoming == 1 }
        _wonCount.value = list.count {
            when (it.wonMatches) {
                1 -> it.pick == it.teamA
                2 -> it.pick == it.teamB
                else -> false
            }
        }
    }
}
