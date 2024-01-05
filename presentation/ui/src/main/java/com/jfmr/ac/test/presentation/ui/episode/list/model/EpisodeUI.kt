package com.jfmr.ac.test.presentation.ui.episode.list.model

import android.os.Parcelable
import com.jfmr.ac.test.domain.model.episode.Episode
import kotlinx.parcelize.Parcelize


@Parcelize
data class EpisodeUI(
    val id: Int,
    val airDate: String = "",
    val characters: List<String> = emptyList(),
    val created: String = "",
    val name: String = "",
    val episode: String = "",
    val url: String = "",
) : Parcelable

internal fun List<Episode>.toUI() =
    map { it.toUI() }

internal fun Episode.toUI() =
    EpisodeUI(
        id = id,
        airDate = airDate,
        characters = characters,
        created = created,
        name = name,
        episode = episode,
        url = url
    )
