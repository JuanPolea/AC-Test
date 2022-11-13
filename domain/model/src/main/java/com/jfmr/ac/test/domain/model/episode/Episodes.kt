package com.jfmr.ac.test.domain.model.episode

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Episodes(
    val results: List<Episode> = emptyList(),
)

@kotlinx.serialization.Serializable
@Parcelize
data class Episode(
    val id: Int,
    @SerializedName("air_date")
    val airDate: String = "",
    val characters: List<String> = emptyList(),
    val created: String = "",
    val name: String = "",
    val episode: String = "",
    val url: String = "",
) : Parcelable
