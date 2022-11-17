package com.jfmr.ac.test.domain.model.character

import com.jfmr.ac.test.domain.model.episode.Episode

data class CharacterDetail(
    val character: Character? = null,
    val episodes: List<Episode> = emptyList(),
)
