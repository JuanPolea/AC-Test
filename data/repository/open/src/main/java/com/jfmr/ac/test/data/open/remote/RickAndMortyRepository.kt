package com.jfmr.ac.test.data.open.remote

import com.jfmr.ac.test.domain.model.Characters
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    fun retrieveCharacters(): Flow<Characters>
}