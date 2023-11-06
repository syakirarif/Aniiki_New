package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aniiki.features.home.paging.HomePagingSource
import com.aniiki.features.home.paging.TopAiringPagingSource
import com.aniiki.features.home.paging.TopMostPopularPagingSource
import com.aniiki.features.home.paging.TopUpcomingPagingSource
import com.aniiki.features.home.ui.HomeUiState
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.json.JSONObject

class HomeRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
//    @WorkerThread
//    fun getAnimeList(
//        page: Int,
//        onStart: () -> Unit,
//        onCompletion: () -> Unit,
//        onError: (String) -> Unit
//    ) = flow {
//        // request API network call asynchronously.
//        animeEndpoints.getAnimeSeason2(page)
//            // handle the case when the API request gets a success response.
//            .suspendOnSuccess {
//                emit(data.data)
//            }
//            // handle the case when the API request is fails.
//            // e.g. internal server error.
//            .onFailure { onError(message()) }
//
//    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    fun getAnimeListPaging(onError: (String) -> Unit): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                HomePagingSource(animeEndpoints = animeEndpoints, onError = onError)
            }
        ).flow
    }

    @WorkerThread
    fun getAnimeTopAiring(
        onStart: () -> Unit,
        onError: (String) -> Unit,
        onCompletion: () -> Unit,
        onSuccess: (List<AnimeResponse>) -> Unit
    ) = flow {
        val response = animeEndpoints.getAnimeTopAiring()
        response.suspendOnSuccess {
            emit(this.data.data)
        }.onSuccess {
            onSuccess(this.data.data)
        }.onError {
            val jsonObject = JSONObject(this.toString())
            onError(jsonObject.getString("error"))
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAnimeTopAiring2(): Flow<HomeUiState> = flow {
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
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = jsonObject.getString("error")
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
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = jsonObject.getString("error")
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
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = jsonObject.getString("error")
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    fun getAnimeTopAiringPaging(): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopAiringPagingSource(animeEndpoints = animeEndpoints)
            }
        ).flow
    }

    fun getAnimeTopUpcomingPaging(): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopUpcomingPagingSource(animeEndpoints = animeEndpoints)
            }
        ).flow
    }

    fun getAnimeTopMostPopularPaging(): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopMostPopularPagingSource(animeEndpoints = animeEndpoints)
            }
        ).flow
    }


}