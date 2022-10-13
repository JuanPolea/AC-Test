package com.jfmr.ac.test.data.di

import com.jfmr.ac.test.data.repository.qualifier.DispatcherDefault
import com.jfmr.ac.test.data.repository.qualifier.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {


    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatcherDefault
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}
