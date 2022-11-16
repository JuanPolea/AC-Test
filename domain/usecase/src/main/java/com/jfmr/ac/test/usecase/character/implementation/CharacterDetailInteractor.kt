package com.jfmr.ac.test.usecase.character.implementation

import com.jfmr.ac.test.data.di.QCharacterRepository
import com.jfmr.ac.test.data.di.QEpisodesRepository
import com.jfmr.ac.test.domain.model.DomainResult
import com.jfmr.ac.test.domain.model.character.Character
import com.jfmr.ac.test.domain.model.character.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.domain.repository.character.CharacterRepository
import com.jfmr.ac.test.domain.repository.episode.EpisodeRepository
import com.jfmr.ac.test.usecase.character.CharacterDetailUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailInteractor @Inject constructor(
    @QCharacterRepository private val characterRepository: CharacterRepository,
    @QEpisodesRepository private val episodeRepository: EpisodeRepository,
) : CharacterDetailUseCase {

    override suspend fun invoke(
        characterId: Int,
        success: (CharacterDetail) -> Unit,
        error: (DomainError) -> Unit,
    ) {
        var characterDetail = CharacterDetail()
        val characterFlow: Flow<DomainResult<Character>> = characterRepository.getCharacterById(characterId)
        characterFlow.flatMapLatest { domainResult: DomainResult<Character> ->
            domainResult.fold(
                {
                    error(it)
                },
                {
                    characterDetail = characterDetail.copy(character = it)
                    success(characterDetail)
                }
            )
            val episodesList: List<String> = characterDetail.character?.episode.orEmpty()
            episodeRepository.episodes(episodesList)
        }.collectLatest { domainResult ->
            domainResult.fold(
                {},
                {
                    characterDetail = characterDetail.copy(episodes = it)
                    success(characterDetail)
                }
            )
        }
    }
}


