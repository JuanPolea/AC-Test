package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import javax.inject.Inject

class UpdateCharacterInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : UpdateCharacterUseCase {

    override suspend operator fun invoke(character: Character) =
        characterRepository.updateCharacter(character)
}
