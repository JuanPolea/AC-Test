package com.jfmr.ac.test.usecase.episode.implementation

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import com.jfmr.ac.test.tests.TestUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetEpisodesInteractorTest {
    private val episodeRepository: EpisodeRepository = mockk()
    private val getEpisodesUseCase = GetEpisodesInteractor(episodeRepository)
    private lateinit var expectedCharacter: Character
    private lateinit var expectedEpisodes: Array<Episode>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        expectedCharacter =
            TestUtils.getObjectFromJson("character.json", Character::class.java) as Character

        expectedEpisodes =
            TestUtils.getObjectFromJson("episodes.json", Array<Episode>::class.java) as Array<Episode>

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    operator fun invoke() = runTest {
        coEvery {
            episodeRepository.episodes(expectedCharacter.episode)
        } returns expectedEpisodes.toList().right()

        getEpisodesUseCase.invoke(
            episodesList = expectedCharacter.episode,
            success = ::success,
            error = ::error
        )
    }

    @Test
    fun invoke_Error() = runTest {
        coEvery {
            episodeRepository.episodes(expectedCharacter.episode)
        } returns RemoteError.Connectivity.left()

        getEpisodesUseCase.invoke(
            episodesList = expectedCharacter.episode,
            success = ::success,
            error = ::error
        )
    }

    private fun success(episode: List<Episode>?) {
        episode?.map {
            assertContains(expectedEpisodes.toList(), it)
        }
    }

    private fun error(domainError: DomainError) {
        assertEquals(RemoteError.Connectivity, domainError)
    }
}
