package com.jfmr.ac.test.data.cache.dao.character.di

import com.jfmr.ac.test.data.cache.dao.character.RemoteKeysDao
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteKeysDaoModule {
    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: RickAndMortyDB): RemoteKeysDao {
        return database.remoteKeysDao()
    }
}