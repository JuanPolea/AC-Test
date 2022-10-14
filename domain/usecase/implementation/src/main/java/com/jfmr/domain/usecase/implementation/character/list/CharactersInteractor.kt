package com.jfmr.domain.usecase.implementation.character.list

import com.jfmr.ac.test.data.open.rickandmorty.datasource.RetrieveCharactersDataSource
import javax.inject.Inject

class CharactersInteractor @Inject constructor(
    val myPaginSource: RetrieveCharactersDataSource,
)
