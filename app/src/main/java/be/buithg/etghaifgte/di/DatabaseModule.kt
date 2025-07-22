package be.buithg.etghaifgte.di

import android.content.Context
import androidx.room.Room
import be.buithg.etghaifgte.data.local.dao.PredictionDao
import be.buithg.etghaifgte.data.local.dao.NoteDao
import be.buithg.etghaifgte.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePredictionDao(db: AppDatabase): PredictionDao = db.predictionDao()

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao()
}
