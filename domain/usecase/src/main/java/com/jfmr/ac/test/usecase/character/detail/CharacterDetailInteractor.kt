package com.jfmr.ac.test.usecase.character.detail

import com.jfmr.ac.test.data.repository.character.di.QCharacterRepository
import com.jfmr.ac.test.data.repository.episode.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
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
            with(CharacterDetail())
            {
                result.fold(
                    ifLeft = {
                        error(it)
                    },
                    ifRight = { character ->
                        merge(episodeRepository.episodes(character.episode))
                            .map {
                                it.fold(
                                    ifLeft = {
                                        success(copy(character = character))
                                    },
                                    ifRight = { episodes ->
                                        success(
                                            copy(
                                                character = character,
                                                episodes = episodes
                                            )
                                        )
                                    }
                                )
                            }.collect()
                    }
                )
            }
        }.collect()
}
