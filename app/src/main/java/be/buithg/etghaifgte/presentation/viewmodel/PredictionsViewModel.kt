package be.buithg.etghaifgte.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.usecase.AddPredictionUseCase
import be.buithg.etghaifgte.domain.usecase.GetPredictionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
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

    private var filterDate: LocalDate? = null

    fun loadPredictions() {
        viewModelScope.launch {
            val list = getPredictionsUseCase()
            _predictions.value = list
            updateCountsWithFilter()
        }
    }

    fun addPrediction(entity: PredictionEntity) {
        viewModelScope.launch {
            addPredictionUseCase(entity)
            loadPredictions()
        }
    }

    fun setFilterDate(date: LocalDate) {
        filterDate = date
        updateCountsWithFilter()
    }

    private fun updateCountsWithFilter() {
        val list = _predictions.value ?: emptyList()
        val filtered = filterDate?.let { date ->
            list.filter {
                runCatching { LocalDateTime.parse(it.dateTime).toLocalDate() }.getOrNull() == date
            }
        } ?: list

        _predictedCount.value = filtered.size
        _upcomingCount.value = filtered.count { it.upcoming == 1 }
        _wonCount.value = filtered.count {
            when (it.wonMatches) {
                1 -> it.pick == it.teamA
                2 -> it.pick == it.teamB
                else -> false
            }
        }
    }
}
