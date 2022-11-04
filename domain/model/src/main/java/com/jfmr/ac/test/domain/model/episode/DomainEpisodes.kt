package com.jfmr.ac.test.domain.model.episode

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Episodes(
    val results: List<DomainEpisode?>? = null,
) : Parcelable

@Parcelize
@Serializable
@Entity(tableName = "episodes")
data class DomainEpisode(

    @PrimaryKey
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
) : Parcelable
