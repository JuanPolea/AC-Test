package com.jfmr.data.repository

import arrow.core.Either
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.Error
import com.jfmr.data.repository.qualifier.QRetrieveRemoteDataSource
import com.jfmr.domain.repository.open.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    @QRetrieveRemoteDataSource private val retrieveCharactersDataSource: RetrieveCharactersDataSource,
    @QDefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ItemsRepository {

    override suspend fun getItems(): Either<Error, Characters?> = withContext(dispatcher) {
        retrieveCharactersDataSource.retrieveCharacters()
    }

    override suspend fun getItemsById(id: Int): Characters {
        return Characters()
    }
}