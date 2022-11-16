package com.jfmr.ac.test.presentation.ui.character.list.viewmodel

import androidx.paging.PagingData
import androidx.paging.map
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterListEvent
import com.jfmr.ac.test.presentation.ui.utils.CharacterUIUtils
import com.jfmr.ac.test.tests.MainCoroutineRule
import com.jfmr.ac.test.tests.character.CharacterUtils
import com.jfmr.ac.test.usecase.character.CharactersUseCase
import com.jfmr.ac.test.usecase.character.UpdateCharacterUseCase
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    @get:Rule
    val instantiationException = MainCoroutineRule()

    private val charactersUseCase: CharactersUseCase = mockk()

    private val characterUI = CharacterUIUtils.expectedCharacterUI
    private val character = CharacterUtils.expectedCharacter

    private val updateCharacterUseCase: UpdateCharacterUseCase = mockk()

    private lateinit var characterListViewModel: CharacterListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coEvery {
            charactersUseCase.invoke()
        } returns flowOf(PagingData.from(listOf(character)))
        coEvery {
            updateCharacterUseCase(character = character)
        } returns flowOf(character.copy(isFavorite = !character.isFavorite))
        characterListViewModel = CharacterListViewModel(
            charactersUseCase = charactersUseCase,
            updateCharacterUseCase = updateCharacterUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getPager() = runTest {
        characterListViewModel.pager.map { paging ->
            paging.map { characterUI ->
                assertEquals(characterUI, characterUI)
            }
        }

    }

    @Test
    fun onEvent() {
        characterListViewModel.onEvent(CharacterListEvent.AddToFavorite(characterUI))

        CharacterListEvent.AddToFavorite(characterUI).apply {
            assertEquals(characterUI, this.character)
        }
    }
}
