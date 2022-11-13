package com.jfmr.ac.test.tests.character

import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.tests.TestUtils

object CharacterUtils {

    val expectedCharacter =
        TestUtils.getObjectFromJson("character.json", Character::class.java) as Character
}
