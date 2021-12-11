package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.data.remote.RetrieveRemoteCharactersDataSource
import com.jfmr.data.repository.qualifier.RetrieveRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersDataSourceModule {

    @RetrieveRemoteDataSource
    @Singleton
    @Binds
    abstract fun bindRetrieveCharactersDataSource(implementation: RetrieveRemoteCharactersDataSource): RetrieveCharactersDataSource
}