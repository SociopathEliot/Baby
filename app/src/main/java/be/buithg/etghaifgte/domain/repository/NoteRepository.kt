package be.buithg.etghaifgte.domain.repository

import be.buithg.etghaifgte.data.local.entity.NoteEntity

interface NoteRepository {
    suspend fun saveNote(entity: NoteEntity)
    suspend fun getNote(key: String): NoteEntity?
}
