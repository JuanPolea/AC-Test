package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.tests.character.CharacterUtils.expectedCharacter
import com.jfmr.ac.test.usecase.character.update.UpdateCharacterInteractor
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

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
        } returns flowOf(expectedCharacter.copy(isFavorite = !expectedCharacter.isFavorite))

        val actual = updateCharacterInteractor.invoke(expectedCharacter)
        actual.collectLatest {
            assertEquals(expectedCharacter.copy(isFavorite = !expectedCharacter.isFavorite), it)
        }

    }
}
