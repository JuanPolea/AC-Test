package com.jfmr.domain.usecase.implementation.character

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.repository.open.character.CharacterRepository
import com.jfmr.ac.test.domain.usecase.open.character.CharactersUseCase
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharactersUseCase {

    override fun characters(): PagingSource<Int, DomainCharacter> =
        characterRepository.characters()
}
