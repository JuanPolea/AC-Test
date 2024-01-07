package com.jfmr.ac.test.data.remote.character.datasource.di

import com.jfmr.ac.test.data.remote.character.CharacterRemoteDataSourceImpl
import com.jfmr.ac.test.data.remote.character.datasource.CharacterRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [CharacterDSModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object CharacterDSModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {

        @Singleton
        @Binds
        fun bindsRemoteCharactersDS(implementation: CharacterRemoteDataSourceImpl): CharacterRemoteDataSource

    }
}
