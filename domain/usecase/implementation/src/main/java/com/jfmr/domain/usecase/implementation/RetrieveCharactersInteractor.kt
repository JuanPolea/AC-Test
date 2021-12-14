package com.jfmr.domain.usecase.implementation

import com.jfmr.ac.test.data.di.QItemRepositoryImpl
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import com.jfmr.domain.repository.open.ItemsRepository
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(
    @QItemRepositoryImpl private val itemsRepository: ItemsRepository
) : RetrieveCharactersUseCase {

    override suspend fun invoke(success: (Characters) -> Unit, error: (DomainError) -> Unit) =
        itemsRepository.getItems().fold(
            {
                error(it)
            },
            {
                it?.let {
                    success(it)
                } ?: error(RemoteError.Null)
            }
        )
}