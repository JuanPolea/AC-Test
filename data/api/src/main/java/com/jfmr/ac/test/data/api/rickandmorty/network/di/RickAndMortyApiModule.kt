package com.jfmr.ac.test.data.api.rickandmorty.network.di

import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPI
import com.jfmr.ac.test.data.api.rickandmorty.network.RickAndMortyAPIServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [RickAndMortyApiModule.Declarations::class])
@InstallIn(SingletonComponent::class)
class RickAndMortyApiModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @Singleton
        @Binds
        fun bindRickAndMortyAPI(implementation: RickAndMortyAPIServiceImpl): RickAndMortyAPI
    }
}
