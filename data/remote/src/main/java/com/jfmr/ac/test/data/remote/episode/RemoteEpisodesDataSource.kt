package com.jfmr.ac.test.data.remote.episode

import android.net.Uri
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.EpisodesResponse
import com.jfmr.ac.test.data.open.rickandmorty.episode.entities.ResultsItem
import com.jfmr.ac.test.data.open.rickandmorty.network.RickAndMortyApiService
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteEpisodesDataSource @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : EpisodesDataSource {

    override fun retrieveEpisodes(episodesList: List<String>): Flow<List<DomainEpisode>> =
        apiService.episodes(
            episodesList.map { Uri.parse(it).lastPathSegment?.toInt() ?: 0 }
        ).filterNotNull()
            .map { response ->
                response.body()?.map { it.toDomain() } ?: emptyList()
            }

    private fun EpisodesResponse?.toDomain(): Episodes =
        Episodes(
            results = this?.results?.filterNotNull()?.map { it.toDomain() }
        )

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
