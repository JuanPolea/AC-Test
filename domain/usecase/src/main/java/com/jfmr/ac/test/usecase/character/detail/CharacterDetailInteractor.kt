package com.jfmr.ac.test.usecase.character.detail

import com.jfmr.ac.test.data.repository.character.di.QCharacterRepository
import com.jfmr.ac.test.data.repository.episode.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterDetailInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : CharacterDetailUseCase {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun invoke(characterId: Int): Flow<Result<CharacterDetail>> = flow {
        characterRepository.getCharacterById(characterId)
            .flatMapConcat { characterResult ->
                characterResult.getOrNull()?.let { character: Character ->
                    episodeRepository.episodes(character.episode).map { episodesResult ->
                        episodesResult.fold(
                            onSuccess = { episodes ->
                                Result.success(CharacterDetail(character, episodes))
                            },
                            onFailure = {
                                Result.success(CharacterDetail(character))
                            }
                        )
                    }
                } ?: flowOf(
                    Result.failure(
                        characterResult.exceptionOrNull() ?: Exception("Character is null")
                    )
                )
            }
            .collect { result ->
                emit(result)
            }
    }
}
