package com.jfmr.ac.test.domain.repository.open.character

import androidx.paging.PagingData
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainResult
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun characters(): Flow<PagingData<DomainCharacter>>

    suspend fun getCharacterById(id: Int): DomainResult<CharacterDetail>
}
