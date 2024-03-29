package com.jfmr.ac.test.data.cache.dao.character

import com.google.gson.Gson
import com.jfmr.ac.test.utils.LocalUtils.expectedLocalCharacter
import com.jfmr.ac.test.utils.LocalUtils.expectedStringCharacter
import org.junit.Test
import kotlin.test.assertEquals

class CharacterConverterTest {


    private val characterConverter = CharacterConverter()


    @Test
    fun domainCharacterToJson() {
        val expected = characterConverter.domainCharacterToJson(expectedLocalCharacter)
        assertEquals(expected, Gson().toJson(expectedLocalCharacter))
    }

    @Test
    fun originToJson() {
        val expected = characterConverter.originToJson(expectedLocalCharacter.origin)
        assertEquals(expected, Gson().toJson(expectedLocalCharacter.origin))
    }


    @Test
    fun locationToJson() {
        val expected = characterConverter.locationToJson(expectedLocalCharacter.location)
        assertEquals(expected, Gson().toJson(expectedLocalCharacter.location))
    }

    @Test
    fun jsonToCharacter() {
        assertEquals(expectedLocalCharacter, characterConverter.jsonToCharacter(expectedStringCharacter))
    }

    @Test
    fun jsonToOrigin() {
        val string = "{ \"name\":\"Earth (C-137)\", \"url\":\"https://rickandmortyapi.com/api/location/1\" }"
        assertEquals(expectedLocalCharacter.origin, characterConverter.jsonToOrigin(string))
    }

    @Test
    fun jsonToLocation() {
        val string = "{\"name\":\"Citadel of Ricks\",\"url\":\"https://rickandmortyapi.com/api/location/3\"}"
        assertEquals(expectedLocalCharacter.location, characterConverter.jsonToLocation(string))
    }

    @Test
    fun listStringToJson() {
        val expectedCharacterEpisodes =
            "[\"https://rickandmortyapi.com/api/episode/1\",\"https://rickandmortyapi.com/api/episode/2\"]"
        val actual = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2"
        )
        assertEquals(expectedCharacterEpisodes, characterConverter.listStringToJson(actual))
    }

    @Test
    fun jsonToListString() {
        val string =
            "[\"https://rickandmortyapi.com/api/episode/1\",\"https://rickandmortyapi.com/api/episode/2\"," +
                    "\"https://rickandmortyapi.com/api/episode/3\",\"https://rickandmortyapi.com/api/episode/4\"," +
                    "\"https://rickandmortyapi.com/api/episode/5\",\"https://rickandmortyapi.com/api/episode/6\"," +
                    "\"https://rickandmortyapi.com/api/episode/7\",\"https://rickandmortyapi.com/api/episode/8\"," +
                    "\"https://rickandmortyapi.com/api/episode/9\",\"https://rickandmortyapi.com/api/episode/10\"," +
                    "\"https://rickandmortyapi.com/api/episode/11\",\"https://rickandmortyapi.com/api/episode/12\"," +
                    "\"https://rickandmortyapi.com/api/episode/13\",\"https://rickandmortyapi.com/api/episode/14\"," +
                    "\"https://rickandmortyapi.com/api/episode/15\",\"https://rickandmortyapi.com/api/episode/16\"," +
                    "\"https://rickandmortyapi.com/api/episode/17\",\"https://rickandmortyapi.com/api/episode/18\"," +
                    "\"https://rickandmortyapi.com/api/episode/19\",\"https://rickandmortyapi.com/api/episode/20\"," +
                    "\"https://rickandmortyapi.com/api/episode/21\",\"https://rickandmortyapi.com/api/episode/22\"," +
                    "\"https://rickandmortyapi.com/api/episode/23\",\"https://rickandmortyapi.com/api/episode/24\"," +
                    "\"https://rickandmortyapi.com/api/episode/25\",\"https://rickandmortyapi.com/api/episode/26\"," +
                    "\"https://rickandmortyapi.com/api/episode/27\",\"https://rickandmortyapi.com/api/episode/28\"," +
                    "\"https://rickandmortyapi.com/api/episode/29\",\"https://rickandmortyapi.com/api/episode/30\"," +
                    "\"https://rickandmortyapi.com/api/episode/31\",\"https://rickandmortyapi.com/api/episode/32\"," +
                    "\"https://rickandmortyapi.com/api/episode/33\",\"https://rickandmortyapi.com/api/episode/34\"," +
                    "\"https://rickandmortyapi.com/api/episode/35\",\"https://rickandmortyapi.com/api/episode/36\"," +
                    "\"https://rickandmortyapi.com/api/episode/37\",\"https://rickandmortyapi.com/api/episode/38\"," +
                    "\"https://rickandmortyapi.com/api/episode/39\",\"https://rickandmortyapi.com/api/episode/40\"," +
                    "\"https://rickandmortyapi.com/api/episode/41\",\"https://rickandmortyapi.com/api/episode/42\"," +
                    "\"https://rickandmortyapi.com/api/episode/43\",\"https://rickandmortyapi.com/api/episode/44\"," +
                    "\"https://rickandmortyapi.com/api/episode/45\",\"https://rickandmortyapi.com/api/episode/46\"," +
                    "\"https://rickandmortyapi.com/api/episode/47\",\"https://rickandmortyapi.com/api/episode/48\"," +
                    "\"https://rickandmortyapi.com/api/episode/49\",\"https://rickandmortyapi.com/api/episode/50\"," +
                    "\"https://rickandmortyapi.com/api/episode/51\"]"

        assertEquals(expectedLocalCharacter.episode, characterConverter.jsonToListString(string))
    }

}
