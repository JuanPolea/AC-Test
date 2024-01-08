package com.jfmr.ac.test.usecase.character.implementation

import androidx.paging.PagingData
import androidx.paging.map
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacter
import com.jfmr.ac.test.usecase.character.list.CharactersInteractor
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CharactersInteractorTest {

    private val characterRepository: CharacterRepository = mockk()
    private val charactersInteractor = CharactersInteractor(characterRepository)

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
            characterRepository.characters()
        } returns flowOf(PagingData.from(listOf(expectedCharacter)))

        val actual = charactersInteractor.invoke()
        actual.map {
            it.map { c ->
                assertEquals(expectedCharacter, c)
            }
        }
    }

}
