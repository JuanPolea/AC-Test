package com.jfmr.ac.test.usecase.character.update.di

import com.jfmr.ac.test.usecase.character.update.UpdateCharacterInteractor
import com.jfmr.ac.test.usecase.character.update.UpdateCharacterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UpdateCharacter

@Module(includes = [UpdateCharacterModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object UpdateCharacterModule {
    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Declarations {
        @UpdateCharacter
        @Singleton
        @Binds
        abstract fun bindUpdateCharacterUseCase(implementation: UpdateCharacterInteractor): UpdateCharacterUseCase
    }
}
