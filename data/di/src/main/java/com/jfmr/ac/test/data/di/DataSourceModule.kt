package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.api.rickandmorty.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.remote.episode.RemoteEpisodesDataSourceImpl
import com.jfmr.ac.test.data.remote.qualifier.QEpisodesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {


    @QEpisodesDataSource
    @Singleton
    @Binds
    fun bindEpisodesDataSource(implementation: RemoteEpisodesDataSourceImpl): RemoteEpisodesDataSource
}
