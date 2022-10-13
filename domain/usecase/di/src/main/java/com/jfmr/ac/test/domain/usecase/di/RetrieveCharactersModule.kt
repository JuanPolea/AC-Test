package com.jfmr.ac.test.domain.usecase.di

import com.jfmr.ac.test.domain.usecase.open.CharacterDetailUseCase
import com.jfmr.ac.test.domain.usecase.open.RetrieveCharactersUseCase
import com.jfmr.domain.usecase.implementation.CharacterDetailInteractor
import com.jfmr.domain.usecase.implementation.RetrieveCharactersInteractor
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

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersModule {

    @RetrieveItemsQualifier
    @Singleton
    @Binds
    abstract fun bindRetrieveCharacters(implementation: RetrieveCharactersInteractor): RetrieveCharactersUseCase

    @CharacterDetailQualifier
    @Singleton
    @Binds
    abstract fun bindRetrieveCharacterDetail(implementation: CharacterDetailInteractor): CharacterDetailUseCase

}
