package com.aniiki.features.home.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniiki.features.home.repository.PeopleDetailRepository
import com.aniiki.features.home.ui.state.PeopleDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
    private val peopleDetailRepository: PeopleDetailRepository
) : ViewModel() {

    private val _charDetail = MutableStateFlow(PeopleDetailUiState())
    val charDetail: StateFlow<PeopleDetailUiState> get() = _charDetail.asStateFlow()

    private val _peopleDetail = MutableStateFlow(PeopleDetailUiState())
    val peopleDetail: StateFlow<PeopleDetailUiState> get() = _peopleDetail.asStateFlow()

    fun getCharacterDetail(charId: String) {
        viewModelScope.launch {
            peopleDetailRepository.getCharacterDetail(charId)
                .collectLatest {
                    _charDetail.value = it
                }
        }
    }

    fun getPeopleDetail(malId: String) {
        viewModelScope.launch {
            peopleDetailRepository.getPeopleDetail(malId)
                .collectLatest {
                    _peopleDetail.value = it
                }
        }
    }
}