package com.jfmr.ac.test.usecase.character

import com.jfmr.ac.test.domain.model.character.Character
import kotlinx.coroutines.flow.Flow

interface UpdateCharacterUseCase {

    operator fun invoke(character: Character): Flow<Character>
}
