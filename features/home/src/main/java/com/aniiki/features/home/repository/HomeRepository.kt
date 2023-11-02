package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.aniiki.features.home.paging.HomePagingSource
import com.aniiki.features.home.paging.TopAiringPagingSource
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

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

    fun getAnimeListPaging(): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                HomePagingSource(animeEndpoints = animeEndpoints)
            }
        ).flow
    }

    fun getAnimeTopAiringPaging(): Flow<PagingData<AnimeResponse>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopAiringPagingSource(animeEndpoints = animeEndpoints)
            }
        ).flow
    }


}