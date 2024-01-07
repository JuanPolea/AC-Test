package com.jfmr.ac.test.data.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherDefault

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatcherIO

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {
    @Provides
    @Singleton
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @DispatcherDefault
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}
