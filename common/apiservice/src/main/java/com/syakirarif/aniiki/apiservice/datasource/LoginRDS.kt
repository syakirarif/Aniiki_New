package com.syakirarif.aniiki.apiservice.datasource

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.utils.ApiResponse
import com.syakirarif.aniiki.apiservice.utils.handleErrorMessage
import com.syakirarif.aniiki.apiservice.utils.handleResponseFailed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
@SuppressLint("CheckResult")
class LoginRDS @Inject constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    fun getAnimeSeason(): Flow<ApiResponse<List<AnimeResponse>>> {
        return channelFlow {
            try {
                val mainResponse = animeEndpoints.getAnimeSeason()

                if (mainResponse.isSuccessful) {
                    val response = mainResponse.body()!!

                    if (response.data != null) {
                        send(
                            ApiResponse.Success(
                                message = "",
                                data = response.data!!
                            )
                        )
                    } else {
                        send(ApiResponse.Error(handleErrorMessage(mainResponse.message())))
                    }
                } else {
                    send(
                        handleResponseFailed(
                            tag = "getAnimeSeason",
                            errorCode = mainResponse.code(),
                            errorBody = mainResponse.errorBody(),
                            message = mainResponse.message()
                        )
                    )
                }
            } catch (e: Exception) {
                send(ApiResponse.Error(handleErrorMessage(e.message.orEmpty())))
            }
        }.flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun getAnimeSeason2(
        page: Int,
        success: () -> Unit,
        error: () -> Unit,
        start: () -> Unit
    ) = flow {
        val response = animeEndpoints.getAnimeSeasonPaging(page)
        response.suspendOnSuccess {
            emit(data.data)
        }.onError {
            error()
        }.onException {
            error()
        }
    }.onStart { start() }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAnimeList(
        page: Int,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        // request API network call asynchronously.
        animeEndpoints.getAnimeSeasonPaging(page)
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                emit(data.data)
            }
            // handle the case when the API request is fails.
            // e.g. internal server error.
            .onFailure { onError(message()) }

    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}