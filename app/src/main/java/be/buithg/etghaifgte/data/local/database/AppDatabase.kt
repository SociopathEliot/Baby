package be.buithg.etghaifgte.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.buithg.etghaifgte.data.local.dao.PredictionDao
import be.buithg.etghaifgte.data.local.entity.PredictionEntity

@Database(entities = [PredictionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun predictionDao(): PredictionDao
}
