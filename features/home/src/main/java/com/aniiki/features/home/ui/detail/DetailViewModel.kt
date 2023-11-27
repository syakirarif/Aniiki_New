package com.aniiki.features.home.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniiki.features.home.repository.DetailRepository
import com.aniiki.features.home.ui.state.DetailUiState
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _animeResponse = MutableStateFlow(AnimeResponse())
    val animeResponse: StateFlow<AnimeResponse> get() = _animeResponse.asStateFlow()

    private val _animePictures = MutableStateFlow(DetailUiState())
    val animePictures: StateFlow<DetailUiState> get() = _animePictures.asStateFlow()

    private val _animeCharacters = MutableStateFlow(DetailUiState())
    val animeCharacters: StateFlow<DetailUiState> get() = _animeCharacters.asStateFlow()

    fun setAnimeResponse(animeResponse: AnimeResponse?) {
        _animeResponse.value = animeResponse ?: AnimeResponse()
    }

    fun getAnimeCharacters(malId: String) {
        viewModelScope.launch {
            detailRepository.getAnimeCharacters(animeId = malId)
                .collectLatest { newData ->
                    _animeCharacters.update { currentState ->
                        currentState.copy(
                            isLoading = newData.isLoading,
                            isError = newData.isError,
                            errorMessage = newData.errorMessage,
                            dataCharacters = newData.dataCharacters
                        )
                    }
                }
        }

    }

    fun getAnimePictures(malId: String) {
        viewModelScope.launch {
            detailRepository.getAnimePictures(animeId = malId)
                .collectLatest { newData ->
                    _animePictures.update { currentState ->
                        currentState.copy(
                            isLoading = newData.isLoading,
                            isError = newData.isError,
                            errorMessage = newData.errorMessage,
                            dataPictures = newData.dataPictures
                        )
                    }
                }
        }
    }

}