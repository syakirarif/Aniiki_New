package com.aniiki.features.home.ui

import androidx.lifecycle.ViewModel
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

) : ViewModel() {
    private val _animeResponse = MutableStateFlow(AnimeResponse())
    val animeResponse: StateFlow<AnimeResponse> = _animeResponse

    fun setAnimeResponse(animeResponse: AnimeResponse?) {
        _animeResponse.value = animeResponse ?: AnimeResponse()
    }
}