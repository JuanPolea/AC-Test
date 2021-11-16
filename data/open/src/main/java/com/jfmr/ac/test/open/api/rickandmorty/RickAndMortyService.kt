package com.jfmr.ac.test.open.api.rickandmorty

import com.jfmr.ac.test.domain.model.Characters
import com.jfmr.ac.test.open.api.rickandmorty.EndPoints.CHARACTER_URL
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {

    @GET(CHARACTER_URL)
    suspend fun retrieveAllCharacters(): Response<Characters>
}