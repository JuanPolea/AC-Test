package com.jfmr.ac.test.usecase.character.update

import com.jfmr.ac.test.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface UpdateCharacterUseCase {

    suspend operator fun invoke(character: Character): Flow<Character>
}
