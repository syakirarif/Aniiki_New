package com.aniiki.features.home.repository.utils

import com.aniiki.features.home.ui.state.DetailUiState
import com.aniiki.features.home.ui.state.HomeUiState
import com.aniiki.features.home.ui.state.PeopleDetailUiState

fun unsuccessfulDetailUiState(message: String): DetailUiState {
    return DetailUiState(
        isLoading = false,
        isError = true,
        errorMessage = message
    )
}

fun unsuccessfulHomeUiState(message: String): HomeUiState {
    return HomeUiState(
        isLoading = false,
        isError = true,
        errorMessage = message
    )
}

fun unsuccessfulPeopleDetailUiState(message: String): PeopleDetailUiState {
    return PeopleDetailUiState(
        isLoading = false,
        isError = true,
        errorMessage = message
    )
}