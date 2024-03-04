package com.jfmr.ac.test.domain.repository.character

import androidx.paging.PagingData
import com.jfmr.ac.test.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

     fun characters(): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Int): Flow<Result<Character>>

    fun updateCharacter(character: Character): Flow<Character>
}
