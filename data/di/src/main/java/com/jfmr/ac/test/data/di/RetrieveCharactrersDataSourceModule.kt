package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.data.remote.RetrieveRemoteCharactersDataSource
import com.jfmr.data.repository.qualifier.QRetrieveRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RetrieveCharactersDataSourceModule {

    @QRetrieveRemoteDataSource
    @Singleton
    @Binds
    abstract fun bindRetrieveCharactersDataSource(implementation: RetrieveRemoteCharactersDataSource): RetrieveCharactersDataSource
}