package com.jfmr.ac.test.usecase.character.list

import androidx.paging.PagingData
import com.jfmr.ac.test.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface CharactersUseCase {
     operator fun invoke(): Flow<PagingData<Character>>
}
