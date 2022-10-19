package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.open.rickandmorty.character.datasource.CharactersDataSource
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.remote.character.RemoteCharactersDataSource
import com.jfmr.ac.test.data.remote.episode.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.remote.qualifier.QCharactersDataSource
import com.jfmr.ac.test.data.remote.qualifier.QEpisodesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @QCharactersDataSource
    @Singleton
    @Binds
    fun bindCharactersDataSource(implementation: RemoteCharactersDataSource): CharactersDataSource

    @QEpisodesDataSource
    @Singleton
    @Binds
    fun bindEpisodesDataSource(implementation: RemoteEpisodesDataSource): EpisodesDataSource
}
