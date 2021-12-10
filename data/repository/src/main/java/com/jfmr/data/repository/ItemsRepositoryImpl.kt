package com.jfmr.data.repository

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.domain.repository.open.ItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ItemsRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ItemsRepository {
    override fun getItems(): Flow<Characters> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemsById(id: Int): Characters {
        TODO("Not yet implemented")
    }
}