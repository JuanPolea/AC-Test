package com.jfmr.ac.test.data.cache.datasource.di

import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSource
import com.jfmr.ac.test.data.cache.datasource.LocalCharacterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [LocalCharacterDSModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object LocalCharacterDSModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {

        @Singleton
        @Binds
        fun bindsLocalLoginDS(implementation: LocalCharacterDataSourceImpl): LocalCharacterDataSource

    }

}