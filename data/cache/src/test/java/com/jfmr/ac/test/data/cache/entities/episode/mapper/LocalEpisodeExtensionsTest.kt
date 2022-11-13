package com.jfmr.ac.test.data.cache.entities.episode.mapper

import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.fromDomain
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.toDomain
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.TestUtils
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LocalEpisodeExtensionsTest {

    private lateinit var localEpisodes: Array<LocalEpisode>
    private lateinit var episodes: Array<Episode>


    @Before
    fun setUp() {
        localEpisodes = TestUtils.getObjectFromJson("episodes.json", Array<LocalEpisode>::class.java) as Array<LocalEpisode>
        episodes = TestUtils.getObjectFromJson("episodes.json", Array<Episode>::class.java) as Array<Episode>
    }

    @Test
    fun toDomain() {
        val actual: List<Episode> = localEpisodes.toList().toDomain()
        assertEquals(episodes.toList(), actual)

        val nullLocalEpisodes: List<LocalEpisode>? = null

        assertEquals(emptyList(), nullLocalEpisodes.toDomain())
    }

    @Test
    fun testToDomain() {
        localEpisodes.forEachIndexed { index, localEpisode ->
            assertEquals(expected = episodes[index], actual = localEpisode.toDomain())
        }
    }

    @Test
    fun fromDomain() {
        val actual: List<LocalEpisode> = episodes.toList().fromDomain()
        assertEquals(localEpisodes.toList(), actual)

        val nullEpisodes: List<Episode>? = null

        assertEquals(emptyList(), nullEpisodes.fromDomain())
    }

    @Test
    fun testFromDomain() {
        episodes.mapIndexed { index, episode ->
            assertEquals(expected = localEpisodes[index], actual = episode.fromDomain())
        }
    }
}
