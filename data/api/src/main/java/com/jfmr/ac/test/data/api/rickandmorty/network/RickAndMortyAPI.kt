package com.jfmr.ac.test.data.api.rickandmorty.network

import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.character.entity.CharactersResponse
import com.jfmr.ac.test.data.api.rickandmorty.dto.episode.entity.EpisodeResponse

const val API_PAGE = "page"

interface RickAndMortyAPI {
    suspend fun characters(page: Int): Result<CharactersResponse>

    suspend fun retrieveCharacterById(characterId: Int): Result<CharacterResponse>

    suspend fun episodes(page: List<Int>): Result<List<EpisodeResponse?>?>
}
