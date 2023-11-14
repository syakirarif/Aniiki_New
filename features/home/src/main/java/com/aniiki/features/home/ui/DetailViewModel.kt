package com.aniiki.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniiki.features.home.repository.DetailRepository
import com.aniiki.features.home.ui.state.DetailUiState
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.anime.childs.Images
import com.syakirarif.aniiki.core.utils.orNullEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _animeResponse = MutableStateFlow(AnimeResponse())
    val animeResponse: StateFlow<AnimeResponse> get() = _animeResponse

    private val _animePictures = MutableStateFlow<List<Images>>(mutableListOf())
    val animePictures: StateFlow<List<Images>> get() = _animePictures

    private val _animePictures2 = MutableStateFlow(DetailUiState())
    val animePictures2: StateFlow<DetailUiState> get() = _animePictures2

    private val _animeCharacters = MutableStateFlow(DetailUiState())
    val animeCharacters: StateFlow<DetailUiState> get() = _animeCharacters

    //    var animeId = "0"
//    private val animeId = animeResponse.value.malId.orNullEmpty()
    val animeId = MutableStateFlow("")

    fun setAnimeResponse(animeResponse: AnimeResponse?) {
        _animeResponse.value = animeResponse ?: AnimeResponse()
        animeId.value = animeResponse?.malId.orNullEmpty()
    }

    fun getAnimeCharacters(malId: String) {
        _animeCharacters.value = detailRepository.getAnimeCharacters(animeId = malId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DetailUiState(isLoading = true)
        ).value
    }

    @ExperimentalCoroutinesApi
    fun getAnimePictures4() {
        viewModelScope.launch {
            animeId.flatMapLatest { anime ->
                detailRepository.getAnimePictures(anime)
            }.collectLatest {
                _animePictures2.value = it
            }
        }

    }

    @ExperimentalCoroutinesApi
    val getAnimePictures5: StateFlow<DetailUiState> =
        animeResponse.flatMapLatest { anime ->
            detailRepository.getAnimePictures(animeId = anime.malId.orNullEmpty())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2500L),
            initialValue = DetailUiState(isLoading = true)
        )

    init {
        getAnimePictures4()
    }


}