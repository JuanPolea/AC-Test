package com.jfmr.domain.repository.di

import com.jfmr.domain.repository.implementation.ItemsRepositoryImpl
import com.jfmr.domain.repository.open.ItemsRepository
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
    abstract fun bindItemRepositoryImpl(implementation: ItemsRepositoryImpl): ItemsRepository

}