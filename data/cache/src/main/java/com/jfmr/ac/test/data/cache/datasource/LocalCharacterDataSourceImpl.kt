package com.jfmr.ac.test.data.cache.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.cache.db.RickAndMortyDB
import com.jfmr.ac.test.data.cache.entities.LocalCharacter
import javax.inject.Inject

class LocalCharacterDataSourceImpl @Inject constructor(
    private val rickAndMortyDB: RickAndMortyDB,
) : LocalCharacterDataSource {

    private val characterDao = rickAndMortyDB.characterDao()

    override fun getCharacters(): PagingSource<Int, LocalCharacter> =
        characterDao.characters()

    override suspend fun getCharacterById(id: Int): LocalCharacter? =
        characterDao.getCharacterById(id)

    override suspend fun updateCharacter(character: LocalCharacter): Int =
        characterDao.updateCharacter(character)

    override suspend fun insert(character: LocalCharacter): Long =
        characterDao.insert(character)

    override fun geLocalDB(): RickAndMortyDB = rickAndMortyDB
}
