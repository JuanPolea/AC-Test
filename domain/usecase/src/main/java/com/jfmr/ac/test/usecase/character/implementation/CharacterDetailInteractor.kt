package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CharacterDetailInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : CharacterDetailUseCase {

    override suspend fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit,
        error: (DomainError) -> Unit,
    ): Unit =
        characterRepository.getCharacterById(characterId).map { result ->
            var characterDetail = CharacterDetail()
            result.fold(
                ifLeft = {
                    error(it)
                },
                ifRight = { character ->
                    merge(episodeRepository.episodes(character.episode))
                        .map {
                            it.fold(
                                ifLeft = {
                                    success(characterDetail.copy(character = character))
                                },
                                ifRight = {
                                    success(
                                        characterDetail.copy(
                                            character = character,
                                            episodes = it
                                        )
                                    )
                                }
                            )
                        }.collect()
                }
            )
        }.collect()
}
