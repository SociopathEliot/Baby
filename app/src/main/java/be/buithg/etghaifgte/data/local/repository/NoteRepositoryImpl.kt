package be.buithg.etghaifgte.data.local.repository

import be.buithg.etghaifgte.data.local.dao.NoteDao
import be.buithg.etghaifgte.data.local.entity.NoteEntity
import be.buithg.etghaifgte.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {
    override suspend fun saveNote(entity: NoteEntity) {
        dao.upsert(entity)
    }

    override suspend fun getNote(key: String): NoteEntity? {
        return dao.getNote(key)
    }
}
