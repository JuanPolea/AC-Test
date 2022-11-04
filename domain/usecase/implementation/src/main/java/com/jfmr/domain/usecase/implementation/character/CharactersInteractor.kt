package com.jfmr.domain.usecase.implementation.character

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.usecase.open.character.CharactersUseCase
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharactersUseCase {

    override fun characters() =
        characterRepository.characters()
}
