package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.ResultsItem
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteEpisodesDataSource @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : EpisodesDataSource {

    override fun retrieveEpisodes(episodesList: List<String>): Flow<List<DomainEpisode>> {
        return flow {
            val episodeResponse = apiService.episodes(
                episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 }
            )
            val result: List<DomainEpisode> = episodeResponse.body()?.map { it.toDomain() } ?: emptyList()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private fun ResultsItem.toDomain() =
        DomainEpisode(
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            id = id,
            url = url
        )

}
