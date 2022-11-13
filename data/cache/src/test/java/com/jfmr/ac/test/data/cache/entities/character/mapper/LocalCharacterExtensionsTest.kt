package com.jfmr.ac.test.data.cache.entities.character.mapper

import com.jfmr.ac.test.data.cache.entities.character.LocalCharacter
import com.jfmr.ac.test.data.cache.entities.character.LocalLocation
import com.jfmr.ac.test.data.cache.entities.character.LocalOrigin
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.fromDomain
import com.jfmr.ac.test.data.cache.entities.character.mapper.LocalCharacterExtensions.toDomain
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.Location
import com.jfmr.ac.test.domain.model.character.Origin
import com.jfmr.ac.test.tests.TestUtils
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LocalCharacterExtensionsTest {

    private lateinit var expectedLocalCharacter: LocalCharacter
    private lateinit var expectedCharacter: Character


    @Before
    fun setUp() {
        expectedLocalCharacter = TestUtils.getObjectFromJson("local_character.json", LocalCharacter::class.java) as LocalCharacter
        expectedCharacter = TestUtils.getObjectFromJson("local_character.json", Character::class.java) as Character
    }

    @Test
    fun localCharacter_toDomain_Character() {
        val actualCharacter = expectedLocalCharacter.toDomain()
        assertEquals(expectedCharacter, actualCharacter)
    }

    @Test
    fun localCharacterNull_toDomain_Character() {
        val actualLocalCharacter: LocalCharacter? = null
        actualLocalCharacter
            .toDomain()
            .apply {
                assertEquals(-1, id)
                assertEquals("", image)
                assertEquals("", gender)
                assertEquals("", species)
                assertEquals("", created)
                assertEquals("", name)
                assertEquals("", url)
                assertEquals(emptyList(), episode)
                assertEquals("", type)
                assertEquals("", status)
                assertEquals(false, isFavorite)

                assertEquals(Origin(), origin)
                assertEquals("", origin.name)
                assertEquals("", origin.url)

                assertEquals(Location(), location)
                assertEquals("", location.name)
                assertEquals("", location.url)

            }
    }


    @Test
    fun characterNull_fromDomain_LocationCharacter() {
        assertEquals(expectedCharacter.fromDomain(), expectedLocalCharacter)
    }

    @Test
    fun character_fromDomain_LocationCharacter() {
        val character: Character? = null
        character
            .fromDomain()
            .apply {
                assertEquals(-1, id)
                assertEquals("", image)
                assertEquals("", gender)
                assertEquals("", species)
                assertEquals("", created)
                assertEquals("", name)
                assertEquals("", url)
                assertEquals(emptyList(), episode)
                assertEquals("", type)
                assertEquals("", status)
                assertEquals(false, isFavorite)

                assertEquals(LocalOrigin(), origin)
                assertEquals("", origin.name)
                assertEquals("", origin.url)

                assertEquals(LocalLocation(), location)
                assertEquals("", location.name)
                assertEquals("", location.url)

            }
    }

}
