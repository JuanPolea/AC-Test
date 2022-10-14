package com.jfmr.domain.usecase.implementation

import com.jfmr.ac.test.data.di.QItemRepositoryImpl
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.open.ItemsRepository
import com.jfmr.ac.test.domain.usecase.open.RetrieveCharactersUseCase
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(
    @QItemRepositoryImpl private val itemsRepository: ItemsRepository,
) : RetrieveCharactersUseCase {

    override suspend fun invoke(success: (Characters) -> Unit, error: (DomainError) -> Unit) =
        itemsRepository.getItems().collect { domainResult ->
            domainResult.fold(
                {
                    error(it)
                },
                {
                    it?.let {
                        success(it)
                    } ?: kotlin.error(RemoteError.Null)
                }
            )
        }

}
