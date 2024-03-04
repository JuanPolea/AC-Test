package com.jfmr.ac.test.data.api.rickandmorty.network

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class RickAndMortyAPIServiceImpl @Inject constructor(private val client: HttpClient) :
    RickAndMortyAPI {

    override suspend fun characters(page: Int): Result<CharactersResponse> =
        runCatching {
            client.get {
                url(EndPoints.CHARACTER_URL)
                parameter("page", page)
            }.body()
        }


    override suspend fun retrieveCharacterById(characterId: Int): Result<CharacterResponse> =
        runCatching {
            client.get(EndPoints.CHARACTER_DETAILED_URL + "$characterId").body()
        }

    override suspend fun episodes(page: List<Int>): Result<List<EpisodeResponse?>?> =
        runCatching {
            client.get(EndPoints.EPISODES_URL + "$page").body()
        }
}