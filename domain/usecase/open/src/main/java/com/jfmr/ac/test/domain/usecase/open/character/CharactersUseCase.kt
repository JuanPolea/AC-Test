package com.jfmr.ac.test.domain.usecase.open.character

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.character.DomainCharacter

interface CharactersUseCase {
    fun characters(): PagingSource<Int, DomainCharacter>
}
