package com.jfmr.ac.test.domain.model.episode


data class Episodes(
    val results: List<DomainEpisode?>? = null,
    val domainInfo: DomainInfo? = null,
)

data class DomainEpisode(
    val airDate: String? = null,
    val characters: List<String?>? = null,
    val created: String? = null,
    val name: String? = null,
    val episode: String? = null,
    val id: Int? = null,
    val url: String? = null,
)

data class DomainInfo(
    val next: String? = null,
    val pages: Int? = null,
    val prev: String? = null,
    val count: Int? = null,
)
