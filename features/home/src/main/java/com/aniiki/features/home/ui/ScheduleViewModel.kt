package com.aniiki.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aniiki.features.home.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {
    private val _animeScheduleState = MutableStateFlow(HomeUiState())
    val animeScheduleState: StateFlow<HomeUiState> = _animeScheduleState.asStateFlow()

    fun fetchAnimeSchedulePaging() {
        viewModelScope.launch {
            _animeScheduleState.value = HomeUiState(isLoading = true)

            val result = scheduleRepository.getAnimeSchedulePaging(onError = {
                _animeScheduleState.value =
                    HomeUiState(isLoading = false, isError = true, errorMessage = it)
            }).cachedIn(viewModelScope)

            _animeScheduleState.value = HomeUiState(isLoading = false, dataPaging = result)
        }
    }

    init {
        fetchAnimeSchedulePaging()
    }
}