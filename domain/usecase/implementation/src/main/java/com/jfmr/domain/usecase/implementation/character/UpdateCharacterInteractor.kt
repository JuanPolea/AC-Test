package com.jfmr.domain.usecase.implementation.character

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.usecase.open.character.UpdateCharacterUseCase
import javax.inject.Inject

class UpdateCharacterInteractor @Inject constructor(
    @QCharacterRepository val characterRepository: CharacterRepository,
) : UpdateCharacterUseCase {

    override suspend fun updateCharacter(character: Character) =
        characterRepository.updateCharacter(character)
}
