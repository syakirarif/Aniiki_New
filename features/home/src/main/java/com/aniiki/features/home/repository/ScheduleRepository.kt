package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.aniiki.features.home.repository.utils.unsuccessfulHomeUiState
import com.aniiki.features.home.ui.state.HomeUiState
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.utils.ErrorEnvelopeMapper
import com.syakirarif.aniiki.core.base.createPager
import com.syakirarif.aniiki.core.utils.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
                    isError = !this.isSuccess,
                    errorMessage = this.messageOrNull ?: "",
                    data = this.data.data
                )
            )
        }.suspendOnError(ErrorEnvelopeMapper) {
            emit(
                unsuccessfulHomeUiState(message = this.codeMessage)
            )
        }.suspendOnException {
            emit(
                unsuccessfulHomeUiState(message = this.messageOrNull ?: "")
            )
        }
    }.flowOn(Dispatchers.IO)
}