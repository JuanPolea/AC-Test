package com.jfmr.ac.test.domain.usecase.open.character

import androidx.paging.PagingData
import com.jfmr.ac.test.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {
    fun characters(): Flow<PagingData<Character>>
}
