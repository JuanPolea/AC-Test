package com.jfmr.ac.test.usecase.character.list.di

import com.jfmr.ac.test.usecase.character.list.CharactersInteractor
import com.jfmr.ac.test.usecase.character.list.CharactersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class GetCharacters

@Module(includes = [CharactersUCModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object CharactersUCModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @GetCharacters
        @Singleton
        @Binds
        abstract fun bindCharacters(implementation: CharactersInteractor): CharactersUseCase
    }
}
