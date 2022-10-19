package com.jfmr.ac.test.data.repository.episode

import androidx.paging.PagingSource
import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.repository.qualifier.QEpisodesDataSource
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.repository.open.episode.EpisodeRepository
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    @QEpisodesDataSource private val episodesDataSource: EpisodesDataSource,
) : EpisodeRepository {

    override fun episodes(): PagingSource<Int, DomainEpisode> =
        episodesDataSource.retrieveEpisodes()

    override suspend fun episodes(episodesList: List<String>): List<DomainEpisode> {
        return episodesDataSource.retrieveEpisodes(episodesList)
    }
}
