package com.jfmr.ac.test.usecase.di

import com.jfmr.implementation.RetrieveCharactersInteractor
import com.jfmr.open.RetrieveCharactersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RetrieveCharactersQualifier

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersModule {

    @RetrieveCharactersQualifier
    @Singleton
    @Binds
    abstract fun bindRetrieveCharactersImpl(implementation: RetrieveCharactersInteractor): RetrieveCharactersUseCase

}