package com.jfmr.ac.test.data.cache.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class LocalEpisode(
    @PrimaryKey
    val id: Int,
    val airDate: String? = "",
    val characters: List<String?>? = emptyList(),
    val created: String? = "",
    val name: String? = "",
    val episode: String? = "",
    val url: String? = "",
)
