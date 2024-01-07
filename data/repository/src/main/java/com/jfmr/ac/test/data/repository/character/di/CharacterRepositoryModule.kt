package com.jfmr.ac.test.data.repository.character.di

import com.jfmr.ac.test.data.repository.character.CharacterRepositoryImpl
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class QCharacterRepository

@InstallIn(SingletonComponent::class)
@Module
interface CharacterRepositoryModule {

    @QCharacterRepository
    @Singleton
    @Binds
    fun bindCharactersRepository(implementation: CharacterRepositoryImpl): CharacterRepository
}
