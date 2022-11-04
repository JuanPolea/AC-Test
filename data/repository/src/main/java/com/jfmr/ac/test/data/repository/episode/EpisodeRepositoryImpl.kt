package com.jfmr.ac.test.data.repository.episode

import com.jfmr.ac.test.data.open.rickandmorty.episode.datasource.EpisodesDataSource
import com.jfmr.ac.test.data.remote.qualifier.QEpisodesDataSource
import com.jfmr.ac.test.domain.model.character.DomainResult
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    @QEpisodesDataSource private val episodesDataSource: EpisodesDataSource,
) : EpisodeRepository {

    override fun episodes(episodesList: List<String>): Flow<DomainResult<List<DomainEpisode>?>> =
        episodesDataSource.retrieveEpisodes(episodesList)

    override suspend fun insert(episodes: Episodes): List<Long> =
        episodesDataSource.insertEpisodes(episodes)


}
