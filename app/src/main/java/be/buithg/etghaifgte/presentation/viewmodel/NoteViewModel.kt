package be.buithg.etghaifgte.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.buithg.etghaifgte.data.local.entity.NoteEntity
import be.buithg.etghaifgte.domain.usecase.GetNoteUseCase
import be.buithg.etghaifgte.domain.usecase.SaveNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
) : ViewModel() {

    private val _noteText = MutableLiveData<String?>()
    val noteText: LiveData<String?> = _noteText

    fun loadNote(key: String) {
        viewModelScope.launch {
            _noteText.value = getNoteUseCase(key)?.note
        }
    }

    fun saveNote(key: String, text: String) {
        viewModelScope.launch {
            saveNoteUseCase(NoteEntity(matchKey = key, note = text))
            _noteText.value = text

        }
    }
}
