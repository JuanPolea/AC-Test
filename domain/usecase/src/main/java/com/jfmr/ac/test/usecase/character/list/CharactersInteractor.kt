package com.jfmr.ac.test.usecase.character.list

import com.jfmr.ac.test.data.repository.character.di.QCharacterRepository
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharactersUseCase {

    override operator fun invoke() =
        characterRepository.characters()
}
