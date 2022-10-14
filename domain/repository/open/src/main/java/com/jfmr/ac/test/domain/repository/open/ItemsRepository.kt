package com.jfmr.ac.test.domain.repository.open

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.DomainCharacter
import com.jfmr.ac.test.domain.model.DomainResult

interface ItemsRepository {

    fun characters(): PagingSource<Int, DomainCharacter>

    suspend fun getItemsById(id: Int): DomainResult<CharacterDetail>
}
