package com.jfmr.ac.test.data.api.rickandmorty.episode.entity

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class EpisodesResponse(
    val results: List<EpisodeResponse?>? = null,
)

@Serializable
data class EpisodeResponse(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("air_date")
    val airDate: String? = null,
    @field:SerializedName("characters")
    val characters: List<String?>? = null,
    @field:SerializedName("created")
    val created: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("episode")
    val episode: String? = null,
    @field:SerializedName("url")
    val url: String? = null,
) 
