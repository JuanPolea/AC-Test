package com.jfmr.ac.test.data.cache.db.di


import android.content.Context
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickAndMortyDB =
        RickAndMortyDB.create(context)

}
