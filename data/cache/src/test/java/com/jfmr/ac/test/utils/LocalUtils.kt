package com.jfmr.ac.test.utils

import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.tests.TestUtils

const val CHARACTER = "character.json"
private const val EPISODE = "episode.json"
private const val EPISODES = "episodes.json"

object LocalUtils {

    val expectedLocalCharacter = TestUtils.getObjectFromJson(CHARACTER, LocalCharacter::class.java) as LocalCharacter

    val expectedStringCharacter = TestUtils.getObjectAsString(CHARACTER)

    val expectedLocalEpisode = TestUtils.getObjectFromJson(EPISODE, LocalEpisode::class.java) as LocalEpisode

    val expectedLocalEpisodes: Array<LocalEpisode> = TestUtils.getObjectFromJson(EPISODES, Array<LocalEpisode>::class.java) as Array<LocalEpisode>

}
