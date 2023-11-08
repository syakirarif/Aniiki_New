package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.PagingData
import com.aniiki.features.home.ui.HomeUiState
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.base.createPager
import com.syakirarif.aniiki.core.utils.getCurrentAnimeSeason
import com.syakirarif.aniiki.core.utils.getCurrentYear
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class HomeRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {

    fun getAnimeListPaging(onError: (String) -> Unit): Flow<PagingData<AnimeResponse>> {
        return createPager(enablePlaceholders = true, onError = onError) { page ->
            animeEndpoints.getAnimeSeasonPaging(
                page = page,
                year = getCurrentYear(),
                season = getCurrentAnimeSeason().lowercase()
            )
        }.flow
    }

    @WorkerThread
    fun getAnimeTopAiring(): Flow<HomeUiState> = flow {
        val response = animeEndpoints.getAnimeTopAiring()
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

    @WorkerThread
    fun getAnimeTopUpcoming(): Flow<HomeUiState> = flow {
        val response = animeEndpoints.getAnimeTopUpcoming()
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

    @WorkerThread
    fun getAnimeTopMostPopular(): Flow<HomeUiState> = flow {
        val response = animeEndpoints.getAnimeTopMostPopular()
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