package com.jfmr.ac.test.data.open.rickandmorty.character.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.character.DomainResult


interface CharactersDataSource {
    fun characters(): PagingSource<Int, DomainCharacter>
    suspend fun retrieveCharacterDetail(characterId: Int): DomainResult<CharacterDetail>
}
