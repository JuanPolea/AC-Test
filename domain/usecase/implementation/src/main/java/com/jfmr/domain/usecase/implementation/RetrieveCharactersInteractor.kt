package com.jfmr.domain.usecase.implementation

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.usecase.open.RetrieveCharactersUseCase
import com.jfmr.domain.repository.di.ItemRepositoryImpl
import com.jfmr.domain.repository.open.ItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(
    @ItemRepositoryImpl private val rickAndMortyRepository: ItemsRepository
) : RetrieveCharactersUseCase {

    override suspend fun invoke(): Flow<Characters> =
        rickAndMortyRepository.getItems()

}