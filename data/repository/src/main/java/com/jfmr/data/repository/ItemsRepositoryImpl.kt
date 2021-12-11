package com.jfmr.data.repository

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.RetrieveCharactersDataSource
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.data.repository.qualifier.RetrieveRemoteDataSource
import com.jfmr.domain.repository.open.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    @RetrieveRemoteDataSource private val retrieveCharactersDataSource: RetrieveCharactersDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ItemsRepository {

    override suspend fun getItems() =
        retrieveCharactersDataSource.retrieveCharacters()

    override suspend fun getItemsById(id: Int): Characters {
        return Characters()
    }
}