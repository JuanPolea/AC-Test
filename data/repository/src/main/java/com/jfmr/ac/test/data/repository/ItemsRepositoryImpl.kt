package com.jfmr.ac.test.data.repository

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.open.rickandmorty.datasource.RetrieveCharactersDataSource
import com.jfmr.ac.test.data.repository.qualifier.QRetrieveRemoteDataSource
import com.jfmr.ac.test.domain.model.DomainCharacter
import com.jfmr.ac.test.domain.repository.open.ItemsRepository
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    @QRetrieveRemoteDataSource private val retrieveCharactersDataSource: RetrieveCharactersDataSource,
) : ItemsRepository {

    override fun characters(): PagingSource<Int, DomainCharacter> =
        retrieveCharactersDataSource.characters()

    override suspend fun getItemsById(id: Int) =
        retrieveCharactersDataSource.retrieveCharacterDetail(id)


}
