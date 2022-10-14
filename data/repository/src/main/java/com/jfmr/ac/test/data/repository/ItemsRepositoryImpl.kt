package com.jfmr.ac.test.data.repository

import com.jfmr.ac.test.data.open.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.qualifier.QRetrieveRemoteDataSource
import com.jfmr.ac.test.domain.repository.open.ItemsRepository
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    @QRetrieveRemoteDataSource private val retrieveCharactersDataSource: RetrieveCharactersDataSource,
) : ItemsRepository {

    override fun getItems() =
        retrieveCharactersDataSource.retrieveCharacters()


    override suspend fun getItemsById(id: Int) =
        retrieveCharactersDataSource.retrieveCharacterDetail(id)
}
