package com.jfmr.ac.test.data.repository.episode

import android.net.Uri
import android.util.Log
import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.data.api.rickandmorty.episode.entity.EpisodeResponse
import com.jfmr.ac.test.data.cache.dao.episode.EpisodeDao
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.fromDomain
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.toDomain
import com.jfmr.ac.test.data.remote.episode.datasource.RemoteEpisodesDataSource
import com.jfmr.ac.test.data.remote.episode.mapper.EpisodeExtensions.toDomain
import com.jfmr.ac.test.data.remote.extensions.tryCall
import com.jfmr.ac.test.data.remote.qualifier.QEpisodesDataSource
import com.jfmr.ac.test.data.repository.episode.mapper.EpisodeResponseExtensions.toEntity
import com.jfmr.ac.test.domain.model.episode.Episodes
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class EpisodeRepositoryImpl @Inject constructor(
    @QEpisodesDataSource private val remoteEpisodesDataSource: RemoteEpisodesDataSource,
    private val episodeDao: EpisodeDao,
) : EpisodeRepository {

    override fun episodes(episodesList: List<String>) = flow {
        tryCall {
            remoteEpisodesDataSource
                .retrieveEpisodes(episodesList)
        }.fold(
            { error ->
                emit(retrieveLocalEpisodes(episodesList) ?: error.left())
            },
            { response ->
                if (response.isSuccessful) {
                    val episodes: List<EpisodeResponse> = response.body()?.filterNotNull() ?: emptyList()
                    Log.e("TAG", episodes.toEntity().toString())
                    if (episodes.isNotEmpty()) {
                        episodeDao.insertEpisodes(episodes.toEntity())
                    }
                    emit(
                        retrieveLocalEpisodes(episodesList)
                            ?: episodes
                                .map { it.toDomain() }
                                .filterNotNull()
                                .right()
                    )
                } else {
                    emit(retrieveLocalEpisodes(episodesList) ?: RemoteError.Connectivity.left())
                }
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
                    .fromDomain()
            )
}

