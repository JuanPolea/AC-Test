package com.jfmr.ac.test.domain.model.episode

data class Episodes(
    val results: List<Episode?>? = null,
)

data class Episode(
    val id: Int,
    val airDate: String? = "",
    val characters: List<String?>? = emptyList(),
    val created: String? = "",
    val name: String? = "",
    val episode: String? = "",
    val url: String? = "",
)
