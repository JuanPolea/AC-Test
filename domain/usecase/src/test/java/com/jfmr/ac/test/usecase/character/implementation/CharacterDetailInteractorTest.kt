package com.jfmr.ac.test.usecase.character.implementation

import arrow.core.left
import arrow.core.right
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.model.error.RemoteError
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
class CharacterDetailInteractorTest {

    private val characterRepository: CharacterRepository = mockk()
    private val characterDetailInteractor = CharacterDetailInteractor(characterRepository)
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
    operator fun invoke() = runTest {
        coEvery {
            characterRepository.getCharacterById(any())
        } returns expectedCharacter.right()
        characterDetailInteractor.invoke(1, ::success, ::error)

    }

    @Test
    fun invoke_Error_DomainError() = runTest {
        coEvery {
            characterRepository.getCharacterById(any())
        } returns RemoteError.Connectivity.left()
        characterDetailInteractor.invoke(1, ::success, ::error)

    }

    private fun success(c: Character) {
        assertEquals(expectedCharacter, c)
    }

    private fun error(domainError: DomainError) {
        assertEquals(RemoteError.Connectivity, domainError)
    }
}
