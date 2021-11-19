package com.jfmr.di.repository.implementation

import com.jfmr.ac.test.implementation.RickAndMortyRepositoryImpl
import com.jfmr.ac.test.open.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class ItemRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class ItemsRepositoryModule {

    @ItemRepositoryImpl
    @Singleton
    @Binds
    abstract fun bindItemRepositoryImpl(implementation: RickAndMortyRepositoryImpl): RickAndMortyRepository

}