package com.aniiki.features.login.usecase

import com.aniiki.features.login.repository.LoginRepository
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun getAnimeSeason(): Flow<Resource<List<AnimeResponse>>> {
        return loginRepository.getAnimeSeason()
    }

    fun getAnimeSeason2(
        page: Int,
        success: () -> Unit,
        error: () -> Unit,
        start: () -> Unit
    ) = loginRepository.getAnimeSeason2(page, success, error, start)

    fun getAnimeList(
        page: Int,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = loginRepository.getAnimeList(page, onStart, onCompletion, onError)
}