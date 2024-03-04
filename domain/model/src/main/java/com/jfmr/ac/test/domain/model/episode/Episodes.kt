package com.jfmr.ac.test.domain.model.episode

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Episodes(
    val results: List<Episode> = emptyList(),
)

@Serializable
data class Episode(
    val id: Int,
    @SerializedName("air_date")
    val airDate: String = "",
    val characters: List<String> = emptyList(),
    val created: String = "",
    val name: String = "",
    val episode: String = "",
    val url: String = "",
)
