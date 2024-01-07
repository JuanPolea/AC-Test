package com.jfmr.ac.test.data.cache.dao.episode.di

import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class EpisodeDaoModule {
    @Provides
    @Singleton
    fun provideEpisodesDao(database: RickAndMortyDB): EpisodeDao {
        return database.episodesDao()
    }
}
