package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.aniiki.features.home.ui.HomeUiState
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.base.createPager
import com.syakirarif.aniiki.core.utils.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class ScheduleRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    fun getAnimeSchedulePaging(onError: (String) -> Unit): Flow<PagingData<AnimeResponse>> {
        return createPager(
            enablePlaceholders = true,
            onError = onError
        ) { page ->
            animeEndpoints.getAnimeSchedulePaging(
                page = page,
                day = getCurrentDay().lowercase()
            )
        }.flow
    }

    @WorkerThread
    fun getAnimeSchedule(): Flow<HomeUiState> = flow {
        val response = animeEndpoints.getAnimeSchedule(getCurrentDay().lowercase())
        response.suspendOnSuccess {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = false,
                    errorMessage = this.response.message(),
                    data = this.data.data
                )
            )
        }.suspendOnError {
            val jsonObject = JSONObject(this.toString())
            val errorMessage = jsonObject.getString("message")
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = errorMessage
                )
            )
        }
    }.flowOn(Dispatchers.IO)
}