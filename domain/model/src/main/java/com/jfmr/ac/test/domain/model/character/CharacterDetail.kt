package com.jfmr.ac.test.domain.model.character

data class CharacterDetail(

    val image: String = "",
    val gender: String = "",
    val species: String = "",
    val created: String = "",
    val origin: Origin? = Origin("", ""),
    val name: String = "",
    val location: Location? = Location("", ""),
    val episode: List<String> = emptyList(),
    val id: Int,
    val type: String = "",
    val url: String = "",
    val status: String = "",
)
