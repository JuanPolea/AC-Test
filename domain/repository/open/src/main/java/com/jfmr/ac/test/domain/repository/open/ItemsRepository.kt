package com.jfmr.ac.test.domain.repository.open

import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.DomainResult
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    fun getItems(): Flow<DomainResult<Characters?>>

    suspend fun getItemsById(id: Int): DomainResult<CharacterDetail>
}
