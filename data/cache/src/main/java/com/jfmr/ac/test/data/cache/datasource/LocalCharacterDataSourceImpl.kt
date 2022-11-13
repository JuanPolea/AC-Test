package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.RemoteKeys
import javax.inject.Inject

class LocalCharacterDataSourceImpl @Inject constructor(
    private val rickAndMortyDB: RickAndMortyDB,
) : LocalCharacterDataSource {

    override fun getCharacters(): PagingSource<Int, LocalCharacter> =
        rickAndMortyDB.characterDao().characters()

    override suspend fun getCharacterById(id: Int): LocalCharacter? =
        rickAndMortyDB.characterDao().getCharacterById(id)

    override suspend fun updateCharacter(character: LocalCharacter): Int =
        rickAndMortyDB.characterDao().updateCharacter(character)

    override suspend fun insert(character: LocalCharacter): Long =
        rickAndMortyDB.characterDao().insert(character)

    override fun geLocalDB(): RickAndMortyDB = rickAndMortyDB

    override suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) =
        rickAndMortyDB.remoteKeysDao().insertAll(remoteKeys)

    override suspend fun insertCharacters(characters: List<LocalCharacter>): List<Long> =
        rickAndMortyDB.characterDao().insertCharacters(characters)

    override suspend fun remoteKeysId(id: Long): RemoteKeys? =
        rickAndMortyDB.remoteKeysDao().remoteKeysId(id)
}
