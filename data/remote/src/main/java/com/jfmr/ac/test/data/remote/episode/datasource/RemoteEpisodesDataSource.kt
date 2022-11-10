package com.jfmr.ac.test.data.remote.episode.datasource

import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.Episode
import kotlinx.coroutines.flow.Flow

interface RemoteEpisodesDataSource {
    fun retrieveEpisodes(episodesList: List<String>): Flow<DomainResult<List<Episode?>?>>
}
