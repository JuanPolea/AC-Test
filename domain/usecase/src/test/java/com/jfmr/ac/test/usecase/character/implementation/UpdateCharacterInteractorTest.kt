package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
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
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCharacterInteractorTest {

    private val characterRepository: CharacterRepository = mockk()
    private val updateCharacterInteractor = UpdateCharacterInteractor(characterRepository)
    private lateinit var expectedCharacter: Character

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        expectedCharacter =
            TestUtils.getObjectFromJson("character.json", Character::class.java) as Character
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
