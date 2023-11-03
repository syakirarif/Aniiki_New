package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aniiki.features.home.paging.HomePagingSource
import com.aniiki.features.home.paging.TopAiringPagingSource
import com.aniiki.features.home.paging.TopMostPopularPagingSource
import com.aniiki.features.home.paging.TopUpcomingPagingSource
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
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
    fun getAnimeTopAiring(onSuccess: () -> Unit, onError: (String) -> Unit) = flow {
        val response = animeEndpoints.getAnimeTopAiring()
        response.suspendOnSuccess {
            emit(this.data.data)
        }.onError {
            val jsonObject = JSONObject(this.toString())
            onError(jsonObject.getString("error"))
        }
    }.onCompletion { onSuccess }.flowOn(Dispatchers.IO)

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