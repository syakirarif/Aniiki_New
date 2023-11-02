package com.aniiki.features.login.repository

import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun getAnimeSeason(): Flow<Resource<List<AnimeResponse>>>
    fun getAnimeSeason2(
        page: Int,
        success: () -> Unit,
        error: () -> Unit,
        start: () -> Unit
    ): Flow<List<AnimeResponse>?>

    fun getAnimeList(
        page: Int,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<AnimeResponse>?>
}