package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailError
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailEvent
import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailUI
import com.jfmr.ac.test.presentation.ui.utils.CharacterUIUtils
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.character.CharacterUtils
import com.jfmr.ac.test.usecase.character.detail.CharacterDetailUseCase
import com.jfmr.ac.test.usecase.character.update.UpdateCharacterUseCase
import com.jfmr.ac.test.usecase.episode.list.GetEpisodesUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
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
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    @Inject
    lateinit var detailViewModel: DetailViewModel
    private val characterUI = CharacterUIUtils.expectedCharacterUI
    private val characterDetailed = CharacterUIUtils.expectedCharacterDetail
    private val expectedCharacterUI = CharacterUtils.expectedCharacter
    private val episodesUI = CharacterUIUtils.expectedEpisodesUI.toList()
    private val characterDetail = CharacterDetailUI(
        characterUI,
        episodes = episodesUI
    )
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        coEvery {
            savedStateHandle
                .get<String>("characterId")
        } returns expectedCharacterUI.id.toString()

        coEvery {
            characterDetailUseCase.invoke(
                expectedCharacterUI.id,
            )
        } returns flowOf(Result.success(characterDetailed))
        coEvery {
            getEpisodesUseCase.invoke(
                episodesList = any(),
            )
        } returns flowOf(Result.success(listOf()))

        coEvery {
            updateCharacterUseCase.invoke(
                any()
            )
        } returns flowOf(
            CharacterUtils.expectedCharacter.copy(isFavorite = !CharacterUtils.expectedCharacter.isFavorite)
        )

        detailViewModel = DetailViewModel(
            characterDetailUseCase = characterDetailUseCase,
            updateCharacterUseCase = updateCharacterUseCase,
            savedStateHandle = savedStateHandle,
        )
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns testDispatcher
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @After
    fun resetMainDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun onEvent_CharacterFound() = runTest {
        CharacterDetailEvent.CharacterFound(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        asserCharacterDetail()
    }

    @Test
    fun onEvent_CharacterNotFound() = runTest {
        detailViewModel.onEvent(CharacterDetailEvent.CharacterNotFound)
        assertEquals(
            expected = CharacterDetailError.CharacterNotFound,
            detailViewModel.characterDetailState.first().error
        )
    }

    @Test
    fun onEvent_EpisodesFound() = runTest {
        CharacterDetailEvent.EpisodesFound(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        detailViewModel.characterDetailState.first().episodes.apply {
            assertEquals(expected = episodesUI, actual = this)
        }
    }

    @Test
    fun onEvent_UpdateCharacter() = runTest {
        CharacterDetailEvent.UpdateCharacter(characterDetail).apply {
            detailViewModel.onEvent(this)
        }
        assertEquals(
            expected = characterUI.isFavorite,
            actual = !detailViewModel.characterDetailState.first().character.isFavorite
        )
    }

    @Test
    fun onEvent_ServerError() = runTest {
        detailViewModel.onEvent(CharacterDetailEvent.CharacterServerError)
        assertEquals(
            expected = CharacterDetailError.ServerError,
            actual = detailViewModel.characterDetailState.first().error
        )
    }

    private suspend fun asserCharacterDetail() {
        val actual: CharacterDetailUI = detailViewModel.characterDetailState.first()
        assertEquals(characterDetail.character, actual.character)
        assertEquals(characterDetail.episodes, actual.episodes)
    }
}
