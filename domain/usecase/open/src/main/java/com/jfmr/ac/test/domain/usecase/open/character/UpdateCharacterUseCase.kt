package com.jfmr.ac.test.domain.usecase.open.character

import com.jfmr.ac.test.domain.model.character.DomainCharacter

interface UpdateCharacterUseCase {

    suspend fun updateCharacter(character: DomainCharacter): DomainCharacter
}
