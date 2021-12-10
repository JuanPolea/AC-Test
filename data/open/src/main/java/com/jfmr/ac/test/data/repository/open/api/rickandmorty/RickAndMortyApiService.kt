package com.jfmr.ac.test.data.repository.open.api.rickandmorty

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.EndPoints.CHARACTER_URL
import com.jfmr.data.remote.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {

    @GET(CHARACTER_URL)
    suspend fun retrieveAllCharacters(): Response<CharacterResponse>
}