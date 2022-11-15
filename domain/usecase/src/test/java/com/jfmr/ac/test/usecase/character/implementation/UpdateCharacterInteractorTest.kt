package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacter
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCharacterInteractorTest {

    private val characterRepository: CharacterRepository = mockk()
    private val updateCharacterInteractor = UpdateCharacterInteractor(characterRepository)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun updateCharacter() = runTest {
        coEvery {
            characterRepository.updateCharacter(any())
        } returns expectedCharacter.copy(isFavorite = !expectedCharacter.isFavorite)

        val actual = updateCharacterInteractor.invoke(expectedCharacter)

        assertEquals(expectedCharacter.copy(isFavorite = !expectedCharacter.isFavorite), actual)
    }
}
