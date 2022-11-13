package com.jfmr.ac.test.data.cache.entities.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "characters")
data class LocalCharacter(
    @PrimaryKey val id: Int,
    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val origin: LocalOrigin = LocalOrigin(),
    val name: String = "",
    val location: LocalLocation = LocalLocation(),
    val episode: List<String> = emptyList(),
    val type: String = "",
    val url: String = "",
    val status: String = "",
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)

data class LocalOrigin(
    val name: String = "",
    val url: String = "",
)

data class LocalLocation(
    val name: String = "",
    val url: String = "",
)
