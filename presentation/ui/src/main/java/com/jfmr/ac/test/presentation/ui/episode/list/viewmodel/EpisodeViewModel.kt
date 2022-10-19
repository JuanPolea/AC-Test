package com.jfmr.ac.test.presentation.ui.episode.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.episode.DomainEpisode
import com.jfmr.ac.test.domain.usecase.di.QEpisodes
import com.jfmr.ac.test.domain.usecase.open.episode.EpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    @QEpisodes private val episodesUseCase: EpisodesUseCase,
) : ViewModel() {

    private val _episodes = MutableStateFlow<List<DomainEpisode>>(emptyList())
    internal val episodes = _episodes.asStateFlow()

    internal fun episodes(episodesList: List<String>) {
        viewModelScope.launch {
            episodesUseCase.episodes(episodesList).collect { episodesList ->
                _episodes.update {
                    episodesList
                }
            }
        }
    }
}
