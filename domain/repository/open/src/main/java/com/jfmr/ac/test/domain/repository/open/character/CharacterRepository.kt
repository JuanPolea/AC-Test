package com.jfmr.ac.test.domain.repository.open.character

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainResult

interface CharacterRepository {

    fun characters(): PagingSource<Int, DomainCharacter>

    suspend fun getCharacterById(id: Int): DomainResult<CharacterDetail>
}
