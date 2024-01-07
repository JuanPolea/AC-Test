package com.jfmr.ac.test.data.api.rickandmorty.network

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.api.rickandmorty.network.EndPoints.CHARACTER_DETAIL_URL
import com.jfmr.ac.test.data.api.rickandmorty.network.EndPoints.CHARACTER_URL
import com.jfmr.ac.test.data.api.rickandmorty.network.EndPoints.EPISODES_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_PAGE = "page"

interface RickAndMortyApiService {

    @GET(CHARACTER_URL)
    suspend fun characters(@Query("page") page: Int): Response<CharactersResponse>

    @GET(CHARACTER_DETAIL_URL)
    suspend fun retrieveCharacterById(@Path("id") characterId: Int): Response<CharacterResponse>

    @GET(EPISODES_URL)
    suspend fun episodes(@Path("ids") page: List<Int>): Response<List<EpisodeResponse?>?>

}
