package com.jfmr.ac.test.usecase.character.detail.di

import com.jfmr.ac.test.usecase.character.detail.CharacterDetailInteractor
import com.jfmr.ac.test.usecase.character.detail.CharacterDetailUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CharacterDetailUC

@Module(includes = [CharacterDetailUCModule.Declarations::class])
@InstallIn(SingletonComponent::class)
object CharacterDetailUCModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @CharacterDetailUC
        @Singleton
        @Binds
        fun bindCharacterDetail(implementation: CharacterDetailInteractor): CharacterDetailUseCase

    }
}
