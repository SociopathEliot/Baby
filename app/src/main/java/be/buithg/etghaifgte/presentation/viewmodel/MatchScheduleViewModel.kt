package be.buithg.etghaifgte.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.buithg.etghaifgte.domain.models.Match
import be.buithg.etghaifgte.domain.usecase.GetCurrentMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MatchScheduleViewModel @Inject constructor(
    private val getCurrentMatchesUseCase: GetCurrentMatchesUseCase
) : ViewModel() {

    private val _matches = MutableLiveData<List<Match>>(emptyList())
    val matches: LiveData<List<Match>> = _matches

    fun loadMatches(dates: List<java.time.LocalDate>) {
        viewModelScope.launch {
            runCatching { getCurrentMatchesUseCase(dates) }
                .onSuccess { _matches.value = it }
                .onFailure { _matches.value = emptyList() }
        }
    }
}

