package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.character.CharacterRepositoryImpl
import com.jfmr.ac.test.data.repository.episode.EpisodeRepositoryImpl
import com.jfmr.ac.test.domain.repository.open.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.open.episode.EpisodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QCharacterRepository

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QEpisodesRepository

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @QCharacterRepository
    @Singleton
    @Binds
    fun bindCharactersRepository(implementation: CharacterRepositoryImpl): CharacterRepository

    @QEpisodesRepository
    @Singleton
    @Binds
    fun bindEpisodesRepository(implementation: EpisodeRepositoryImpl): EpisodeRepository

}
