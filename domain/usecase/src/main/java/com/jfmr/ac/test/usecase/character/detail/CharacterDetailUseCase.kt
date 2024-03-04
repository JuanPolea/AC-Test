package com.jfmr.ac.test.usecase.character.detail

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import kotlinx.coroutines.flow.Flow

interface CharacterDetailUseCase {
    suspend operator fun invoke(characterId: Int): Flow<Result<CharacterDetail>>
}
