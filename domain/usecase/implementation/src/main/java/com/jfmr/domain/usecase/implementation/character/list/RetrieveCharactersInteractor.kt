package com.jfmr.domain.usecase.implementation.character.list

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.di.QItemRepositoryImpl
import com.jfmr.ac.test.domain.model.DomainCharacter
import com.jfmr.ac.test.domain.repository.open.ItemsRepository
import com.jfmr.ac.test.domain.usecase.open.RetrieveCharactersUseCase
import javax.inject.Inject

class RetrieveCharactersInteractor @Inject constructor(
    @QItemRepositoryImpl private val itemsRepository: ItemsRepository,
) : RetrieveCharactersUseCase {

    override fun characters(): PagingSource<Int, DomainCharacter> =
        itemsRepository.characters()
}
