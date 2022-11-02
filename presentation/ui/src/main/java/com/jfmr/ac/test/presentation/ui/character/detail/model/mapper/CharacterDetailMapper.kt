package com.jfmr.ac.test.presentation.ui.character.detail.model.mapper

import com.jfmr.ac.test.presentation.ui.character.detail.model.CharacterDetailState

object CharacterDetailMapper {

    internal fun toCharacterStateLoading(messageResource: Int) = CharacterDetailState.Loading(messageResource)

}
