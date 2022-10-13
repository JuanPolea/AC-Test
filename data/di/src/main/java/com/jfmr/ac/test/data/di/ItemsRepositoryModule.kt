package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.ItemsRepositoryImpl
import com.jfmr.ac.test.domain.repository.open.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
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
