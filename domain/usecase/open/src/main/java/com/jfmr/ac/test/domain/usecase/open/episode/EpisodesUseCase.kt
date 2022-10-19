package com.jfmr.ac.test.domain.usecase.open.episode

import androidx.paging.PagingSource
import com.jfmr.ac.test.domain.model.episode.DomainEpisode

interface EpisodesUseCase {
    fun episodes(): PagingSource<Int, DomainEpisode>
    suspend fun episodes(episodesList: List<String>): List<DomainEpisode>
}
