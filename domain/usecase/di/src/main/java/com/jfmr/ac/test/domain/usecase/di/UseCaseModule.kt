package com.jfmr.ac.test.domain.usecase.di

import com.jfmr.ac.test.domain.usecase.open.character.CharacterDetailUseCase
import com.jfmr.ac.test.domain.usecase.open.character.CharactersUseCase
import com.jfmr.ac.test.domain.usecase.open.character.UpdateCharacterUseCase
import com.jfmr.ac.test.domain.usecase.open.episode.GetEpisodesUseCase
import com.jfmr.domain.usecase.implementation.character.CharacterDetailInteractor
import com.jfmr.domain.usecase.implementation.character.CharactersInteractor
import com.jfmr.domain.usecase.implementation.character.UpdateCharacterInteractor
import com.jfmr.domain.usecase.implementation.episode.GetEpisodesInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GetCharacters

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CharacterDetail

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UpdateCharacter

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GetEpisode

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @GetCharacters
    @Singleton
    @Binds
    abstract fun bindCharacters(implementation: CharactersInteractor): CharactersUseCase

    @CharacterDetail
    @Singleton
    @Binds
    abstract fun bindCharacterDetail(implementation: CharacterDetailInteractor): CharacterDetailUseCase

    @GetEpisode
    @Singleton
    @Binds
    abstract fun bindEpisodesUseCase(implementation: GetEpisodesInteractor): GetEpisodesUseCase

    @UpdateCharacter
    @Singleton
    @Binds
    abstract fun bindUpdateCharacterUseCase(implementation: UpdateCharacterInteractor): UpdateCharacterUseCase

}
