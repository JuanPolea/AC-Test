package com.jfmr.ac.test.presentation.ui.episode.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.episode.Episode
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.usecase.di.GetEpisode
import com.jfmr.ac.test.usecase.episode.GetEpisodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    @GetEpisode private val getEpisodesUseCase: GetEpisodesUseCase,
) : ViewModel() {

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    internal val episodes = _episodes.asStateFlow()

    internal fun episodes(episodesList: List<String>) =
        viewModelScope.launch {
            getEpisodesUseCase
                .invoke(
                    episodesList = episodesList,
                    success = ::success,
                    error = ::error
                )
        }

    private fun success(episodes: List<Episode>?) =
        episodes?.let { list ->
            _episodes.update {
                list
            }
        }

    private fun error(domainError: DomainError) {
        Timber.i(domainError.toString())
        _episodes.update {
            emptyList()
        }
    }
}
