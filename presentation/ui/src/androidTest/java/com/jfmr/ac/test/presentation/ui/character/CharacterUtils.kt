package com.jfmr.ac.test.presentation.ui.character

import com.jfmr.ac.test.presentation.ui.character.list.model.CharacterUI
import com.jfmr.ac.test.presentation.ui.character.list.model.LocationUI
import com.jfmr.ac.test.presentation.ui.character.list.model.OriginUI
import com.jfmr.ac.test.presentation.ui.episode.list.model.EpisodeUI

object CharacterUtils {
    val characterUI = CharacterUI(
        id = 1,
        image = "",
        gender = "Male",
        species = "Human",
        created = "",
        origin = OriginUI(name = "Earth", url = ""),
        name = "Rick Sánchez",
        LocationUI(name = "Earth", url = ""),
        episode = emptyList(),
        type = "Type",
        url = "",
        status = "Alive",
        isFavorite = false,
    )
    val episodes = mutableListOf<EpisodeUI>().apply {
        repeat(10) {
            add(it, EpisodeUI(
                id = it,
                airDate = "airDate $it",
                characters = emptyList(),
                created = "Created $it",
                name = "Name $it",
                episode = "episode $it",
                url = "url $it"
            )
            )
        }
    }
}
