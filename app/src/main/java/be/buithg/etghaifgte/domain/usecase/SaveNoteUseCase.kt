package be.buithg.etghaifgte.domain.usecase

import be.buithg.etghaifgte.data.local.entity.NoteEntity
import be.buithg.etghaifgte.domain.repository.NoteRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(entity: NoteEntity) {
        repository.saveNote(entity)
    }
}
