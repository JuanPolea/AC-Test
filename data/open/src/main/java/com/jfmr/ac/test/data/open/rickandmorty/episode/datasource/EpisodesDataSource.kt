package com.jfmr.ac.test.data.open.rickandmorty.episode.datasource

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.episode.DomainEpisode

interface EpisodesDataSource {
    fun retrieveEpisodes(): PagingSource<Int, DomainEpisode>
    suspend fun retrieveEpisodes(episodesList: List<String>): List<DomainEpisode>
}
