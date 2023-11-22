package com.aniiki.features.home.ui.state

import com.syakirarif.aniiki.apiservice.response.character.AnimeCharacterResponse

data class PeopleDetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dataCharacterDetail: AnimeCharacterResponse = AnimeCharacterResponse()
)