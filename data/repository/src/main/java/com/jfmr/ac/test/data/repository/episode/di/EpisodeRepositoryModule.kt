package com.jfmr.ac.test.data.repository.episode.di

import com.jfmr.ac.test.data.repository.episode.EpisodeRepositoryImpl
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QEpisodesRepository

@Module(includes = [EpisodeRepositoryModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object EpisodeRepositoryModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @QEpisodesRepository
        @Binds
        @Singleton
        fun bindEpisodeRepository(implementation: EpisodeRepositoryImpl): EpisodeRepository
    }
}
