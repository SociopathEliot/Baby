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

    fun loadPredictions() {
        viewModelScope.launch {
            _predictions.value = getPredictionsUseCase()
        }
    }

    fun addPrediction(entity: PredictionEntity) {
        viewModelScope.launch {
            addPredictionUseCase(entity)
            loadPredictions()
        }
    }
}
