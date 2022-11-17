package com.jfmr.ac.test.usecase.character.implementation

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacter
import com.jfmr.ac.test.tests.episodes.EpisodeUtils
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailUCInteractorTest {

    private val characterRepository: CharacterRepository = mockk()
    private val episodeRepository: EpisodeRepository = mockk()
    private val characterDetailInteractor = CharacterDetailInteractor(
        characterRepository = characterRepository,
        episodeRepository = episodeRepository)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    operator fun invoke() = runTest {
        coEvery {
            episodeRepository.episodes(any())
        } returns flowOf(EpisodeUtils.expectedEpisodeList.toList().right())

        coEvery {
            characterRepository.getCharacterById(any())
        } returns flowOf(expectedCharacter.right())
        characterDetailInteractor.invoke(1, ::success, ::error)
    }

    @Test
    fun invoke_Error_DomainError() = runTest {
        coEvery {
            episodeRepository.episodes(any())
        } returns flowOf(RemoteError.Connectivity.left())
        coEvery {
            characterRepository.getCharacterById(any())
        } returns flowOf(RemoteError.Connectivity.left())
        characterDetailInteractor.invoke(1, ::success, ::error)
    }

    private fun success(c: CharacterDetail) {
        assertEquals(expectedCharacter, c.character)
    }

    private fun error(domainError: DomainError) {
        assertEquals(RemoteError.Connectivity, domainError)
    }
}
