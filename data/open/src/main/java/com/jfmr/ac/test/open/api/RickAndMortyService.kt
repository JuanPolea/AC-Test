package com.jfmr.ac.test.open.api

import com.jfmr.ac.test.model.CharactersResponse
import com.jfmr.ac.test.open.api.EndPoints.CHARACTER_URL
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {

    @GET(CHARACTER_URL)
    suspend fun retrieveAllCharacters(): Response<CharactersResponse>
}