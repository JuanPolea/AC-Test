package com.jfmr.ac.test.usecase.character

import com.jfmr.ac.test.domain.model.character.Character

interface UpdateCharacterUseCase {

    suspend operator fun invoke(character: Character): Character
}
