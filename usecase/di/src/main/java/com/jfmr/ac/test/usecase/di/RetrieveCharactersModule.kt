package com.jfmr.ac.test.usecase.di

import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import com.jfmr.implementation.RetrieveCharactersInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RetrieveItemsQualifier

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersModule {

    @RetrieveItemsQualifier
    @Singleton
    @Binds
    abstract fun bindRetrieveCharactersImpl(implementation: RetrieveCharactersInteractor): RetrieveCharactersUseCase

}