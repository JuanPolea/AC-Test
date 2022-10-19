package com.jfmr.ac.test.data.open.rickandmorty.network

import com.jfmr.ac.test.data.open.rickandmorty.character.entities.CharacterDetailResponse
import com.jfmr.ac.test.data.open.rickandmorty.character.entities.CharactersResponse
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.ResultsItem
import com.jfmr.ac.test.data.open.rickandmorty.network.EndPoints.CHARACTER_DETAIL_URL
import com.jfmr.ac.test.data.open.rickandmorty.network.EndPoints.CHARACTER_URL
import com.jfmr.ac.test.data.open.rickandmorty.network.EndPoints.EPISODES_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_PAGE = "page"

interface RickAndMortyApiService {

    @GET(CHARACTER_URL)
    suspend fun characters(@Query("page") page: Int): CharactersResponse

    @GET(CHARACTER_DETAIL_URL)
    suspend fun retrieveCharacterById(@Path("id") characterId: Int): Response<CharacterDetailResponse>

    @GET(EPISODES_URL)
    suspend fun episodes(@Path("ids") page: List<Int>): Response<List<ResultsItem>>

}
