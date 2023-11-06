package com.aniiki.features.login.usecase

import com.aniiki.features.login.repository.LoginRepository
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun getAnimeSeason(): Flow<Resource<List<AnimeResponse>>> {
        return emptyFlow()
    }
}