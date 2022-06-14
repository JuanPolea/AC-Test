package com.jfmr.domain.usecase.implementation

import com.jfmr.ac.test.data.di.QItemRepositoryImpl
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.usecase.open.CharacterDetailUseCase
import com.jfmr.domain.repository.open.ItemsRepository
import javax.inject.Inject

class CharacterDetailInteractor @Inject constructor(
    @QItemRepositoryImpl private val itemsRepository: ItemsRepository,
) : CharacterDetailUseCase {

    override suspend fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit, error: (DomainError) -> Unit,
    ) =
        itemsRepository
            .getItemsById(characterId)
            .fold(
                {
                    error(it)
                },
                {
                    success(it)
                }
            )
}