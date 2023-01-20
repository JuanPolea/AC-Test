package com.jfmr.ac.test.data.cache.entities.episode.mapper

import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.domain.model.episode.Episode

object LocalEpisodeExtensions {

    fun List<LocalEpisode>?.toDomain(): List<Episode> =
        this?.let {
            it.map { localEpisode ->
                localEpisode.toDomain()
            }
        } ?: emptyList()

    fun LocalEpisode.toDomain() =
        Episode(
            id = id,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )

    fun List<Episode>?.fromDomain(): List<LocalEpisode> =
        this?.let {
            it.map { episode ->
                episode.fromDomain()
            }
        } ?: emptyList()

    fun Episode.fromDomain() =
        LocalEpisode(
            id = id,
            airDate = airDate,
            characters = characters,
            created = created,
            name = name,
            episode = episode,
            url = url
        )
}
