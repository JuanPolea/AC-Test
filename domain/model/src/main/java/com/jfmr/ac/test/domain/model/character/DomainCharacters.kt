package com.jfmr.ac.test.domain.model.character


data class DomainCharacters(
    val results: List<DomainCharacter?>? = null,
    val info: Info? = null,
)


data class Info(
    val next: String? = null,
    val pages: Int? = null,
    val prev: String? = null,
    val count: Int? = null,
)

data class DomainCharacter(
    val image: String? = null,
    val gender: String? = null,
    val species: String? = null,
    val created: String? = null,
    val origin: Origin? = null,
    val name: String? = null,
    val location: Location? = null,
    val episode: List<String?>? = null,
    val id: Int,
    val type: String? = null,
    val url: String? = null,
    val status: String? = null,
)
