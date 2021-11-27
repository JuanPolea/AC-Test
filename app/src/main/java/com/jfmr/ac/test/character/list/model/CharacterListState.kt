package com.jfmr.ac.test.character.list.model

sealed class CharacterListState {
    object Initial : CharacterListState()
    data class Success(val tema: String) : CharacterListState()
}
