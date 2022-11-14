package com.jfmr.ac.test.usecase.di

import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.CharactersUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.character.implementation.CharacterDetailInteractor
import com.jfmr.ac.test.usecase.character.implementation.CharactersInteractor
import com.jfmr.ac.test.usecase.character.implementation.UpdateCharacterInteractor
import com.jfmr.ac.test.usecase.episode.GetEpisodesUseCase
import com.jfmr.ac.test.usecase.episode.implementation.GetEpisodesInteractor
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
annotation class CharacterDetailUC

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UpdateCharacter

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GetEpisode

@InstallIn(SingletonComponent::class)
@Module(includes = [UseCaseModule.Declarations::class])
object UseCaseModule {

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Declarations {

        @GetCharacters
        @Singleton
        @Binds
        abstract fun bindCharacters(implementation: CharactersInteractor): CharactersUseCase

        @CharacterDetailUC
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

}
