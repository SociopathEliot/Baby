package be.buithg.etghaifgte.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.buithg.etghaifgte.data.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE matchKey = :key LIMIT 1")
    suspend fun getNote(key: String): NoteEntity?
}
