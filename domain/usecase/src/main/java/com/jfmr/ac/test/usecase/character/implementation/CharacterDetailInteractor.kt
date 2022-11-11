package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import javax.inject.Inject

class CharacterDetailInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
) : CharacterDetailUseCase {

    override suspend fun invoke(
        characterId: Int,
        success: (Character) -> Unit, error: (DomainError) -> Unit,
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
