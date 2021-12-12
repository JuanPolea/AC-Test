package com.jfmr.domain.repository.open

import arrow.core.Either
import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.domain.model.Error

interface ItemsRepository {

    suspend fun getItems(): Either<Error, Characters?>

    suspend fun getItemsById(id: Int): Characters
}