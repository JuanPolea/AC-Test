package com.jfmr.ac.test.data.cache.dao.episode

import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.TestUtils
import org.junit.Assert.*
import org.junit.Test
import kotlin.test.BeforeTest

class EpisodeConverterTest {


    private lateinit var expectedEpisodes: Array<Episode>
    private lateinit var expectedLocalEpisodes: Array<LocalEpisode>

    private val episodeConverter = EpisodeConverter()
    private val episodeFile = "episode.json"
    private val episodesFile = "episodes.json"
    lateinit var expectedStringEpisode: String
    lateinit var expectedStringEpisodes: String

    @BeforeTest
    fun setUp() {
        expectedEpisodes = TestUtils.getObjectFromJson(episodesFile, Array<Episode>::class.java) as Array<Episode>
        expectedLocalEpisodes = TestUtils.getObjectFromJson(episodesFile, Array<LocalEpisode>::class.java) as Array<LocalEpisode>
        expectedStringEpisode = TestUtils.getObjectAsString(episodeFile)
        expectedStringEpisodes = TestUtils.getObjectAsString(episodesFile)
    }

    @Test
    fun jsonToLocalEpisode() {
        assertEquals(expectedLocalEpisodes.first(), episodeConverter.jsonToLocalEpisode(expectedStringEpisode))
    }

    @Test
    fun localEpisodeToJson() {
        assertEquals(expectedStringEpisode.replace(" ", ""), episodeConverter.localEpisodeToJson(expectedLocalEpisodes.first()).replace(" ", ""))
    }


    @Test
    fun localEpisodesToJson() {
        assertEquals(expectedStringEpisodes.replace(" ", ""), episodeConverter.localEpisodesToJson(expectedLocalEpisodes.toList()).replace(" ", ""))
    }

    @Test
    fun jsonToLocalEpisodes() {
        episodeConverter
            .jsonToLocalEpisodes(expectedStringEpisodes)
            .forEachIndexed { index, localEpisode ->
                kotlin.test.assertEquals(expectedLocalEpisodes[index], localEpisode)
            }
    }

}
