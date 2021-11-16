package com.jfmr.ac.test.open

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    fun retrieveCharacters(): Flow<Characters>
}