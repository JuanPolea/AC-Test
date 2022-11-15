package com.jfmr.ac.test.data.repository.utils

import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.tests.TestUtils

const val CHARACTER = "character.json"
private const val EPISODES = "episodes.json"

object LocalUtils {

    val expectedLocalCharacter = TestUtils.getObjectFromJson(CHARACTER, LocalCharacter::class.java) as LocalCharacter

    val expectedLocalEpisodes = TestUtils.getObjectFromJson(EPISODES, Array<LocalEpisode>::class.java) as Array<LocalEpisode>
}
