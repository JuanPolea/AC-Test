package com.jfmr.domain.usecase.implementation

import com.jfmr.ac.test.data.di.QItemRepositoryImpl
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import com.jfmr.domain.repository.open.ItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(
    @QItemRepositoryImpl private val rickAndMortyRepository: ItemsRepository
) : RetrieveCharactersUseCase {

    override suspend fun invoke(): Flow<Characters> =
        rickAndMortyRepository.getItems()

}