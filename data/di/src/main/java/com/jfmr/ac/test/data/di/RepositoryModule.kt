package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.data.remote.RickAndMortyRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RickAndMortyRemoteRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @RickAndMortyRemoteRepositoryImpl
    @Singleton
    @Binds
    abstract fun bindItemRepositoryImpl(implementation: RickAndMortyRemoteRepository): RetrieveCharactersDataSource

}