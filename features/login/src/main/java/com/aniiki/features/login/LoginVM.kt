package com.aniiki.features.login

import androidx.lifecycle.ViewModel
import com.aniiki.features.login.usecase.LoginUseCase
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginUiState(
    val data: List<AnimeResponse> = listOf(),
    val isLoading: Boolean = false
)

@HiltViewModel
class LoginVM @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {


}