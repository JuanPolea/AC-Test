package com.jfmr.ac.test.domain.usecase.di

import com.jfmr.ac.test.domain.usecase.open.character.CharacterDetailUseCase
import com.jfmr.ac.test.domain.usecase.open.character.CharactersUseCase
import com.jfmr.ac.test.domain.usecase.open.episode.EpisodesUseCase
import com.jfmr.domain.usecase.implementation.character.CharacterDetailInteractor
import com.jfmr.domain.usecase.implementation.character.CharactersInteractor
import com.jfmr.domain.usecase.implementation.episode.EpisodesInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RetrieveItemsQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CharacterDetailQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QEpisodes

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @RetrieveItemsQualifier
    @Singleton
    @Binds
    abstract fun bindCharacters(implementation: CharactersInteractor): CharactersUseCase

    @CharacterDetailQualifier
    @Singleton
    @Binds
    abstract fun bindCharacterDetail(implementation: CharacterDetailInteractor): CharacterDetailUseCase

    @QEpisodes
    @Singleton
    @Binds
    abstract fun bindEpisodesUseCase(implementation: EpisodesInteractor): EpisodesUseCase

}
