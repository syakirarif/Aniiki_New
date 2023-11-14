package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import com.aniiki.features.home.ui.state.DetailUiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class DetailRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    //    @WorkerThread
    fun getAnimePictures(animeId: String): Flow<DetailUiState> = flow {

        val response = animeEndpoints.getAnimePictures(animeId = animeId)

        response.suspendOnSuccess {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = false,
                    errorMessage = this.response.message(),
                    dataPictures = this.data.data ?: mutableListOf()
                )
            )
        }
            .suspendOnError {
                val jsonObject = JSONObject(this.toString())
                val errorMessage = jsonObject.getString("message")
                emit(
                    DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = errorMessage
                    )
                )
            }
            .suspendOnException {
                emit(
                    DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = this.message()
                    )
                )
            }
            .suspendOnFailure {
                emit(
                    DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = this.message()
                    )
                )
            }

    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAnimeCharacters(animeId: String): Flow<DetailUiState> = flow {

        val response = animeEndpoints.getAnimeCharacters(animeId = animeId)

        response.suspendOnSuccess {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = false,
                    errorMessage = this.response.message(),
                    dataCharacters = this.data.data ?: mutableListOf()
                )
            )
        }.suspendOnError {
            val jsonObject = JSONObject(this.toString())
            val errorMessage = jsonObject.getString("message")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = errorMessage
                )
            )
        }

    }.flowOn(Dispatchers.IO)
}