package com.jfmr.ac.test.data.repository.episode

import android.net.Uri
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.entities.LocalEpisode
import com.jfmr.ac.test.data.remote.qualifier.QEpisodesDataSource
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    @QEpisodesDataSource private val remoteEpisodesDataSource: RemoteEpisodesDataSource,
    private val episodeDao: EpisodeDao,
) : EpisodeRepository {

    override fun episodes(episodesList: List<String>): Flow<Either<DomainError, List<Episode>>> =
        remoteEpisodesDataSource
            .retrieveEpisodes(episodesList)
            .map { domainResult ->
                domainResult
                    .fold(
                    { error ->
                        retrieveLocalEpisodes(episodesList) ?: error.left()
                    },
                    { episodes ->
                        episodes
                            ?.filterNotNull()
                            ?.let {
                                episodeDao.insertEpisodes(it.toEntity())
                            }
                        retrieveLocalEpisodes(episodesList) ?: RemoteError.Connectivity.left()
                    }
                )
            }

    private suspend fun retrieveLocalEpisodes(episodesList: List<String>) =
        episodeDao
            .episodes(
                episodesList
                    .map {
                        Uri.parse(it).lastPathSegment?.toInt() ?: 0
                    }
            )
            ?.toDomain()
            ?.right()

    override suspend fun insert(episodes: Episodes): List<Long> =
        episodeDao
            .insertEpisodes(
                episodes
                    .results
                    ?.filterNotNull()
                    ?.toEntity()
                    .orEmpty()
            )

    private fun List<Episode>.toEntity() =
        map {
            it.toEntity()
        }

    private fun Episode.toEntity() =
        LocalEpisode(
            id = id,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )


    private fun List<LocalEpisode>.toDomain() =
        map { it.toDomain() }

    private fun LocalEpisode.toDomain() =
        Episode(
            id = id,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )


}
