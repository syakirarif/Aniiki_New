package com.aniiki.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aniiki.features.home.repository.HomeRepository
import com.aniiki.features.home.ui.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _animeTopUpcomingState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState())
    val animeTopUpcomingState: StateFlow<HomeUiState> get() = _animeTopUpcomingState

    val animeTopAiring: StateFlow<HomeUiState> = homeRepository.getAnimeTopAiring().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState(isLoading = true)
    )


    val animeTopMostPopularState: StateFlow<HomeUiState> =
        homeRepository.getAnimeTopMostPopular().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true)
        )

    private val _animeSeasonState = MutableStateFlow(HomeUiState())
    val animeSeasonState: StateFlow<HomeUiState> = _animeSeasonState.asStateFlow()

    fun fetchAnimePaging() {
        viewModelScope.launch {
            _animeSeasonState.value = HomeUiState(isLoading = true)

            val result = homeRepository.getAnimeListPaging(onError = {
                _animeSeasonState.value =
                    HomeUiState(isLoading = false, errorMessage = it, isError = true)
            }).cachedIn(viewModelScope)

            _animeSeasonState.value = HomeUiState(isLoading = false, dataPaging = result)
        }
    }

    fun fetchAnimeUpcoming() {
        viewModelScope.launch {
//            _animeTopUpcomingState.value = homeRepository.getAnimeTopUpcoming().stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = HomeUiState(isLoading = true)
//            ).value
            homeRepository.getAnimeTopUpcoming().collectLatest {
                _animeTopUpcomingState.value = it
            }
        }
    }

    init {
        fetchAnimePaging()
        fetchAnimeUpcoming()
    }
}