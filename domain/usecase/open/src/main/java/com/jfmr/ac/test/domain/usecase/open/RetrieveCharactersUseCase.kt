package com.jfmr.ac.test.domain.usecase.open

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.DomainCharacter

interface RetrieveCharactersUseCase {
    fun characters(): PagingSource<Int, DomainCharacter>
}
