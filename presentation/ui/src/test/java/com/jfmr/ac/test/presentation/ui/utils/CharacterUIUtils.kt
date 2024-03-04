package com.jfmr.ac.test.presentation.ui.utils

import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.EpisodeUI
import com.jfmr.ac.test.tests.TestUtils
import com.jfmr.ac.test.tests.episodes.EPISODES_LIST

const val CHARACTER = "character.json"

object CharacterUIUtils {

    val expectedCharacterUI =
        TestUtils.getObjectFromJson(CHARACTER, CharacterUI::class.java) as CharacterUI
    val expectedCharacterDetail =
        TestUtils.getObjectFromJson(CHARACTER, CharacterDetail::class.java) as CharacterDetail

    val expectedEpisodesUI =
        TestUtils.getObjectFromJson(EPISODES_LIST, Array<EpisodeUI>::class.java) as Array<EpisodeUI>
}
