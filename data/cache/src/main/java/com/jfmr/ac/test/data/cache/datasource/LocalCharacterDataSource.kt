package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys

interface LocalCharacterDataSource {

     fun getCharacters(): PagingSource<Int, LocalCharacter>
    suspend fun getCharacterById(id: Int): LocalCharacter?
    suspend fun updateCharacter(character: LocalCharacter): Int
    suspend fun insert(character: LocalCharacter): Long
    suspend fun geLocalDB(): RickAndMortyDB
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>): List<Long>
    suspend fun insertCharacters(characters: List<LocalCharacter>): List<Long>
    suspend fun remoteKeysId(id: Long): RemoteKeys?
}
