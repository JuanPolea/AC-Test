package com.jfmr.domain.usecase.implementation.character

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.open.character.CharacterRepository
import com.jfmr.ac.test.domain.usecase.open.character.CharacterDetailUseCase
import javax.inject.Inject

class CharacterDetailInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharacterDetailUseCase {

    override suspend fun invoke(
        characterId: Int,
        success: (DomainCharacter) -> Unit, error: (DomainError) -> Unit,
    ) =
        characterRepository
            .getCharacterById(characterId)
            .fold(
                {
                    error(it)
                },
                {
                    success(it)
                }
            )
}
