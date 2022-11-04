package com.jfmr.ac.test.data.di


import android.content.Context
import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.dao.character.RemoteKeysDao
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.db.character.RickAndMortyRemoteMediator
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDB =
        RickAndMortyDB.create(context)

    @Provides
    @Singleton
    fun provideCharacterDao(database: RickAndMortyDB): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeyDao(database: RickAndMortyDB): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideMediator(
        database: RickAndMortyDB,
        networkService: RickAndMortyApiService,
    ) = RickAndMortyRemoteMediator(
        localDB = database,
        networkService = networkService
    )

    @Provides
    @Singleton
    fun provideEpisodesDao(database: RickAndMortyDB): EpisodeDao {
        return database.episodesDao()
    }
}
