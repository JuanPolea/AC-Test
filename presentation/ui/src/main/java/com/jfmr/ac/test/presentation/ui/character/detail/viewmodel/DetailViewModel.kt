package com.jfmr.ac.test.presentation.ui.character.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfmr.ac.test.domain.model.CharacterDetail
import com.jfmr.ac.test.domain.model.error.DomainError
import com.jfmr.ac.test.usecase.di.CharacterDetailQualifier
import com.jfmr.ac.test.usecase.open.CharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @CharacterDetailQualifier private val characterDetailUseCase: CharacterDetailUseCase,
) : ViewModel() {


    init {
        viewModelScope.launch {
            characterDetailUseCase.invoke(
                1,
                ::success,
                ::error,
            )
        }
    }

    private fun success(characterDetail: CharacterDetail) {

    }

    private fun error(domainError: DomainError) {

    }
}