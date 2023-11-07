package com.aniiki.features.home.ui

import androidx.paging.PagingData
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<AnimeResponse> = mutableListOf(),
    val dataPaging: Flow<PagingData<AnimeResponse>> = emptyFlow(),
)
