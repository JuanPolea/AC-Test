package com.jfmr.ac.test.data.remote.episode.datasource.di

import com.jfmr.ac.test.data.remote.episode.RemoteEpisodesDataSourceImpl
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QEpisodesDataSource

@InstallIn(SingletonComponent::class)
@Module
interface EpisodeDSModule {


    @QEpisodesDataSource
    @Singleton
    @Binds
    fun bindEpisodesDataSource(implementation: RemoteEpisodesDataSourceImpl): RemoteEpisodesDataSource
}
