package com.jfmr.ac.test.data.paging.di

import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.paging.RickAndMortyRemoteMediator
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteMediatorModule {

    @Provides
    @Singleton
    fun provideMediator(
        localCharacterDataSource: LocalCharacterDataSource,
        characterRemoteDataSource: CharacterRemoteDataSource,
    ) = RickAndMortyRemoteMediator(
        localCharacterDataSource = localCharacterDataSource,
        characterRemoteDataSource = characterRemoteDataSource
    )
}