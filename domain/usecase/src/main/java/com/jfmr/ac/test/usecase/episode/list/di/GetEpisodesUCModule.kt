package com.jfmr.ac.test.usecase.episode.list.di

import com.jfmr.ac.test.usecase.episode.list.GetEpisodesInteractor
import com.jfmr.ac.test.usecase.episode.list.GetEpisodesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GetEpisode

@Module(includes = [GetEpisodesUCModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object GetEpisodesUCModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @GetEpisode
        @Singleton
        @Binds
        abstract fun bindEpisodesUseCase(implementation: GetEpisodesInteractor): GetEpisodesUseCase

    }
}
