package com.jfmr.ac.test.usecase.open

import com.jfmr.ac.test.domain.model.Characters

interface RetrieveCharactersUseCase {
    suspend fun invoke(): Characters?
}