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

@InstallIn(SingletonComponent::class)
@Module
interface EpisodeRepositoryModule {

    @QEpisodesRepository
    @Singleton
    @Binds
    fun bindEpisodesRepository(implementation: EpisodeRepositoryImpl): EpisodeRepository

}
