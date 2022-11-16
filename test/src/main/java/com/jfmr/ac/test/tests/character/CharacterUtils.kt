package com.jfmr.ac.test.tests.character

import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharacterResponse
import com.jfmr.ac.test.data.api.rickandmorty.character.entity.CharactersResponse
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.tests.TestUtils

private const val CHARACTER = "character.json"
private const val CHARACTERS = "characters.json"
const val CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES = "character_by_id_success_empty_episodes.json"
const val EMPTY = "empty.json"


object CharacterUtils {

    val expectedCharacter =
        TestUtils.getObjectFromJson(CHARACTER, Character::class.java) as Character
    val expectedCharacterResponse =
        TestUtils.getObjectFromJson(CHARACTER, CharacterResponse::class.java) as CharacterResponse
    val expectedCharacterResponseWithoutEpisodes =
        TestUtils.getObjectFromJson(CHARACTER_BY_ID_SUCCESS_WITHOUT_EPISODES, CharacterResponse::class.java) as CharacterResponse
    val expectedEmptyCharacterResponse =
        TestUtils.getObjectFromJson(EMPTY, CharacterResponse::class.java) as CharacterResponse

    val expectedCharactersResponse =
        TestUtils.getObjectFromJson(CHARACTERS, CharactersResponse::class.java) as CharactersResponse


}
