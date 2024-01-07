package com.jfmr.ac.test.usecase.character.update

import com.jfmr.ac.test.data.repository.character.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import javax.inject.Inject

class UpdateCharacterInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : UpdateCharacterUseCase {

    override suspend operator fun invoke(character: Character) =
        characterRepository.updateCharacter(character)
}
