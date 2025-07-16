package be.buithg.etghaifgte.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.buithg.etghaifgte.data.local.entity.PredictionEntity

@Dao
interface PredictionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prediction: PredictionEntity)

    @Query("SELECT * FROM predictions ORDER BY id DESC")
    suspend fun getAll(): List<PredictionEntity>
}
