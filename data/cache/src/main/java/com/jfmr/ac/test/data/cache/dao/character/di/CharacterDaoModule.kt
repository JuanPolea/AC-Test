package com.jfmr.ac.test.data.cache.dao.character.di

import com.jfmr.ac.test.data.cache.dao.character.CharacterDao
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterDaoModule {
    @Provides
    @Singleton
    fun provideCharacterDao(database: RickAndMortyDB): CharacterDao {
        return database.characterDao()
    }
}
