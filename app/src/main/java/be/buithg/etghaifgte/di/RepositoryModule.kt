package be.buithg.etghaifgte.di

import be.buithg.etghaifgte.data.repository.MatchRepositoryImpl
import be.buithg.etghaifgte.domain.repository.MatchRepository
import be.buithg.etghaifgte.data.local.repository.PredictionRepositoryImpl
import be.buithg.etghaifgte.domain.repository.PredictionRepository
import be.buithg.etghaifgte.data.local.repository.NoteRepositoryImpl
import be.buithg.etghaifgte.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMatchRepository(
        impl: MatchRepositoryImpl
    ): MatchRepository

    @Binds
    @Singleton
    abstract fun bindPredictionRepository(
        impl: PredictionRepositoryImpl
    ): PredictionRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        impl: NoteRepositoryImpl
    ): NoteRepository
}

