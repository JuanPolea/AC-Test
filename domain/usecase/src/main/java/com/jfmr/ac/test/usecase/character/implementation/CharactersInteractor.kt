package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.usecase.character.CharactersUseCase
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharactersUseCase {

    override fun characters() =
        characterRepository.characters()
}
