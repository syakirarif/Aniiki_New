package com.aniiki.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aniiki.features.home.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private val _animeSchedulePagingState = MutableStateFlow(HomeUiState())
    val animeSchedulePagingState: StateFlow<HomeUiState> = _animeSchedulePagingState.asStateFlow()

    fun fetchAnimeSchedulePaging() {
        viewModelScope.launch {
            _animeSchedulePagingState.value = HomeUiState(isLoading = true)

            val result = scheduleRepository.getAnimeSchedulePaging(onError = {
                _animeSchedulePagingState.value =
                    HomeUiState(isLoading = false, isError = true, errorMessage = it)
            }).cachedIn(viewModelScope)

            _animeSchedulePagingState.value = HomeUiState(isLoading = false, dataPaging = result)
        }
    }

    val animeScheduleState: StateFlow<HomeUiState> =
        scheduleRepository.getAnimeSchedule().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true)
        )

    init {
        fetchAnimeSchedulePaging()
    }
}