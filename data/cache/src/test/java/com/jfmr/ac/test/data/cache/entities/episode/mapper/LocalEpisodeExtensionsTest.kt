package com.jfmr.ac.test.data.cache.entities.episode.mapper

import com.jfmr.ac.test.data.cache.entities.episode.LocalEpisode
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.fromDomain
import com.jfmr.ac.test.data.cache.entities.episode.mapper.LocalEpisodeExtensions.toDomain
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.tests.episodes.EpisodeUtils.expectedEpisodes
import com.jfmr.ac.test.utils.LocalUtils.expectedLocalEpisodes
import org.junit.Test
import kotlin.test.assertEquals

class LocalEpisodeExtensionsTest {


    @Test
    fun toDomain() {
        val actual: List<Episode> = expectedLocalEpisodes.toList().toDomain()
        assertEquals(expectedEpisodes.toList(), actual)

        val nullLocalEpisodes: List<LocalEpisode>? = null

        assertEquals(emptyList(), nullLocalEpisodes.toDomain())
    }

    @Test
    fun testToDomain() {
        expectedLocalEpisodes.forEachIndexed { index, localEpisode ->
            assertEquals(expected = expectedEpisodes[index], actual = localEpisode.toDomain())
        }
    }

    @Test
    fun fromDomain() {
        val actual: List<LocalEpisode> = expectedEpisodes.toList().fromDomain()
        assertEquals(expectedLocalEpisodes.toList(), actual)

        val nullEpisodes: List<Episode>? = null

        assertEquals(emptyList(), nullEpisodes.fromDomain())
    }

    @Test
    fun testFromDomain() {
        expectedEpisodes.mapIndexed { index, episode ->
            assertEquals(expected = expectedLocalEpisodes[index], actual = episode.fromDomain())
        }
    }
}
