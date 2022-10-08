package com.jfmr.ac.test.data.di

import com.jfmr.data.repository.ItemsRepositoryImpl
import com.jfmr.domain.repository.open.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class QItemRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class ItemsRepositoryModule {

    @QItemRepositoryImpl
    @Singleton
    @Binds
    abstract fun bindItemRepositoryImpl(implementation: ItemsRepositoryImpl): ItemsRepository

}
