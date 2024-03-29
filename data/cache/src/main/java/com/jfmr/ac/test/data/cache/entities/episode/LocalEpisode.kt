package com.jfmr.ac.test.data.cache.entities.episode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
data class LocalEpisode(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "air_date")
    @SerializedName("air_date")
    val airDate: String = "",
    val characters: List<String> = emptyList(),
    val created: String = "",
    val name: String = "",
    val episode: String = "",
    val url: String = "",
)
