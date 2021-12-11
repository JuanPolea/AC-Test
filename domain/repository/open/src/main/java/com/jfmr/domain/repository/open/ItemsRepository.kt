package com.jfmr.domain.repository.open

import com.jfmr.ac.test.domain.model.Characters

interface ItemsRepository {

    suspend fun getItems(): Characters?

    suspend fun getItemsById(id: Int): Characters
}