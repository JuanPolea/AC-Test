package com.jfmr.ac.test.data.open.rickandmorty.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.DomainCharacter
import com.jfmr.ac.test.domain.model.DomainResult


interface RetrieveCharactersDataSource {
    fun characters(): PagingSource<Int, DomainCharacter>
    suspend fun retrieveCharacterDetail(characterId: Int): DomainResult<CharacterDetail>
}
