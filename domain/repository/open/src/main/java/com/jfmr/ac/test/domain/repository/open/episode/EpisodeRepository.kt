package com.jfmr.ac.test.domain.repository.open.episode

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.episode.DomainEpisode

interface EpisodeRepository {

    fun episodes(): PagingSource<Int, DomainEpisode>
    suspend fun episodes(episodesList: List<String>): List<DomainEpisode>
}
