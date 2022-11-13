package com.jfmr.ac.test.domain.model.character

data class Character(
    val id: Int,
    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val origin: Origin = Origin(),
    val name: String = "",
    val location: Location = Location(),
    val episode: List<String> = emptyList(),
    val type: String = "",
    val url: String = "",
    val status: String = "",
    val isFavorite: Boolean = false,
)

data class Origin(
    val name: String = "",
    val url: String = "",
)

data class Location(
    val name: String = "",
    val url: String = "",
)

