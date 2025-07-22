package be.buithg.etghaifgte.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val matchKey: String,
    val note: String
)
