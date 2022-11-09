package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.LocalCharacter

interface LocalCharacterDataSource {

    fun getCharacters(): PagingSource<Int, LocalCharacter>
    suspend fun getCharacterById(id: Int): LocalCharacter?
    suspend fun updateCharacter(character: LocalCharacter): Int
    suspend fun insert(character: LocalCharacter): Long
    fun geLocalDB(): RickAndMortyDB

}
