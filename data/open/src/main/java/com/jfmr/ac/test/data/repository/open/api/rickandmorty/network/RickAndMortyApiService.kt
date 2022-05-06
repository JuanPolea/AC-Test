package com.jfmr.ac.test.data.repository.open.api.rickandmorty.network

import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterDetailResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.entities.CharacterResponse
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.EndPoints.CHARACTER_DETAIL_URL
import com.jfmr.ac.test.data.repository.open.api.rickandmorty.network.EndPoints.CHARACTER_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApiService {

    @GET(CHARACTER_URL)
    suspend fun retrieveAllCharacters(): Response<CharacterResponse>

    @GET(CHARACTER_DETAIL_URL)
    suspend fun retrieveCharacterById(@Path("id") characterId: Int): Response<CharacterDetailResponse>
}