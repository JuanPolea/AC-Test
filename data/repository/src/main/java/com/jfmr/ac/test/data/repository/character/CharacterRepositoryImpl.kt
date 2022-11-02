package com.jfmr.ac.test.data.repository.character

import com.jfmr.ac.test.data.open.rickandmorty.character.datasource.CharactersDataSource
import com.jfmr.ac.test.data.remote.qualifier.QCharactersDataSource
import com.jfmr.ac.test.domain.model.character.DomainCharacter
import com.jfmr.ac.test.domain.repository.open.character.CharacterRepository
import javax.inject.Inject


class CharacterRepositoryImpl @Inject constructor(
    @QCharactersDataSource private val charactersDataSource: CharactersDataSource,
) : CharacterRepository {

    override fun characters() =
        charactersDataSource.fetchCharacters()

    override suspend fun getCharacterById(id: Int) =
        charactersDataSource.retrieveCharacterDetail(id)

    override suspend fun updateCharacter(character: DomainCharacter): DomainCharacter =
        charactersDataSource.updateCharacter(character)

}
