package com.jfmr.di.repository.implementation

import com.jfmr.ac.test.data.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.ac.test.implementation.RetrieveCharactersDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RetrieveCharacterImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersDataSourceModule {

    @RetrieveCharacterImpl
    @Singleton
    @Binds
    abstract fun bindItemRepositoryImpl(implementation: RetrieveCharactersDataSourceImpl): RetrieveCharactersDataSource

}