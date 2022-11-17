package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailUI
import com.jfmr.ac.test.presentation.ui.character.detail.model.toDetailError
import com.jfmr.ac.test.presentation.ui.character.list.model.toUI
import com.jfmr.ac.test.presentation.ui.utils.CharacterUIUtils
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.character.CharacterUtils
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.episode.GetEpisodesUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val dispatcher = MainCoroutineRule()

    private val characterDetailUseCase: CharacterDetailUseCase = mockk()
    private val updateCharacterUseCase: UpdateCharacterUseCase = mockk()
    private val getEpisodesUseCase: GetEpisodesUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    @Inject
    lateinit var detailViewModel: DetailViewModel
    private val characterUI = CharacterUIUtils.expectedCharacterUI
    private val expectedCharacter = CharacterUtils.expectedCharacter
    private val episodesUI = CharacterUIUtils.expectedEpisodesUI.toList()
    private val characterDetail = CharacterDetailUI(
        characterUI,
        episodes = episodesUI
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery {
            savedStateHandle
                .get<String>("characterId")
        } returns expectedCharacter.id.toString()

        coEvery {
            characterDetailUseCase.invoke(
                expectedCharacter.id,
                ::characterDetailSuccess,
                ::error
            )
        } just runs
        coEvery {
            getEpisodesUseCase.invoke(
                episodesList = any(),
                { any() },
                { any() },
            )
        } just runs

        detailViewModel = DetailViewModel(
            characterDetailUseCase = characterDetailUseCase,
            updateCharacterUseCase = updateCharacterUseCase,
            savedStateHandle = savedStateHandle,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun onEvent() = runTest {

        coEvery {
            getEpisodesUseCase.invoke(
                episodesList = any(),
                { any() },
                { any() },
            )
        } just runs

        CharacterDetailEvent.CharacterFound(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()


        CharacterDetailEvent.EpisodesFound(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()

        CharacterDetailEvent.CharacterNotFound.apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()

        CharacterDetailEvent.UpdateCharacter(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()

        CharacterDetailEvent.CharacterServerError.apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()

    }

    private suspend fun asserCharacterDetail() {
        val actual: CharacterDetailUI = detailViewModel.characterDetailState.first()
        assertEquals(characterDetail.character, actual.character)
        assertEquals(characterDetail.episodes, actual.episodes)
    }

    private fun characterDetailSuccess(character: com.jfmr.ac.test.domain.model.character.CharacterDetail) {
        assertEquals(characterDetail.character, character.character?.toUI())
    }

    private fun error(domainError: DomainError) {
        assertEquals(domainError.toDetailError(), CharacterDetailError.CharacterNotFound)
    }
}
