package be.buithg.etghaifgte.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.buithg.etghaifgte.data.local.entity.PredictionEntity
import be.buithg.etghaifgte.domain.usecase.AddPredictionUseCase
import be.buithg.etghaifgte.domain.usecase.GetPredictionsUseCase
import be.buithg.etghaifgte.domain.usecase.GetCurrentMatchesUseCase
import be.buithg.etghaifgte.domain.models.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class PredictionsViewModel @Inject constructor(
    private val addPredictionUseCase: AddPredictionUseCase,
    private val getPredictionsUseCase: GetPredictionsUseCase,
    private val getCurrentMatchesUseCase: GetCurrentMatchesUseCase
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

    private val sport = "soccer"
    private val league = "eng.1"

    private fun winnerTeam(match: Match): Int {
        val scoreA = match.scoreA ?: 0
        val scoreB = match.scoreB ?: 0
        return when {
            scoreA > scoreB -> 1
            scoreB > scoreA -> 2
            else -> 0
        }
    }

    private fun isUpcoming(item: PredictionEntity): Boolean {
        if (item.upcoming == 1) return true
        val dt = runCatching { LocalDateTime.parse(item.dateTime) }.getOrNull()
        return dt?.isAfter(LocalDateTime.now()) ?: false
    }

    fun loadPredictions() {
        viewModelScope.launch {
            val list = getPredictionsUseCase()
            refreshUpcomingMatches(list)
            _predictions.value = getPredictionsUseCase()
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
        _upcomingCount.value = filtered.count { isUpcoming(it) }
        _wonCount.value = filtered.count { prediction ->
            if (isUpcoming(prediction)) return@count false
            when (prediction.wonMatches) {
                1 -> prediction.pick == prediction.teamA
                2 -> prediction.pick == prediction.teamB
                else -> false
            }
        }
    }

    private suspend fun refreshUpcomingMatches(list: List<PredictionEntity>) {
        val upcomingList = list.filter { isUpcoming(it) }
        if (upcomingList.isEmpty()) return

        val matches = runCatching { getCurrentMatchesUseCase(sport, league) }.getOrNull() ?: return

        upcomingList.forEach { prediction ->
            val match = matches.find { it.dateTimeGMT == prediction.dateTime }
            if (match != null && match.matchEnded) {
                val winner = winnerTeam(match)
                val updated = prediction.copy(upcoming = 0, wonMatches = winner)
                addPredictionUseCase(updated)
            }
        }
    }
}
