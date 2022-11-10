package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys
import javax.inject.Inject

class LocalCharacterDataSourceImpl @Inject constructor(
    private val rickAndMortyDB: RickAndMortyDB,
) : LocalCharacterDataSource {

    private val characterDao = rickAndMortyDB.characterDao()
    private val remoteKeyDao = rickAndMortyDB.remoteKeysDao()

    override fun getCharacters(): PagingSource<Int, LocalCharacter> =
        characterDao.characters()

    override suspend fun getCharacterById(id: Int): LocalCharacter? =
        characterDao.getCharacterById(id)

    override suspend fun updateCharacter(character: LocalCharacter): Int =
        characterDao.updateCharacter(character)

    override suspend fun insert(character: LocalCharacter): Long =
        characterDao.insert(character)

    override fun geLocalDB(): RickAndMortyDB = rickAndMortyDB

    override suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) =
        remoteKeyDao.insertAll(remoteKeys)

    override suspend fun insertCharacters(characters: List<LocalCharacter>): List<Long> =
        characterDao.insertCharacters(characters)

    override suspend fun remoteKeysId(id: Long): RemoteKeys? =
        remoteKeyDao.remoteKeysId(id)
}
