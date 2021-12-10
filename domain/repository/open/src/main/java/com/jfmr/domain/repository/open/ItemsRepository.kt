package com.jfmr.domain.repository.open

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getItems(): Flow<Characters>

    suspend fun getItemsById(id: Int): Characters
}