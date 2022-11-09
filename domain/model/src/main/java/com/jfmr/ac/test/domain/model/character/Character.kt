package com.jfmr.ac.test.domain.model.character

data class Character(
    val id: Int,
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val created: String? = null,
    val origin: Origin? = null,
    val name: String? = null,
    val location: Location? = null,
    val episode: List<String?>? = null,
    val type: String? = null,
    val url: String? = null,
    val status: String? = null,
    val isFavorite: Boolean = false,
)

data class Origin(
    val name: String? = null,
    val url: String? = null,
)

data class Location(
    val name: String? = "",
    val url: String? = "",
)
