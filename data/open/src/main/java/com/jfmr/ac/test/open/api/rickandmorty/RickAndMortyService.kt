package com.jfmr.ac.test.open.api.rickandmorty

import com.jfmr.ac.test.open.api.rickandmorty.EndPoints.CHARACTER_URL
import com.jfmr.ac.test.open.api.rickandmorty.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {

    @GET(CHARACTER_URL)
    suspend fun retrieveAllCharacters(): Response<CharacterResponse>
}