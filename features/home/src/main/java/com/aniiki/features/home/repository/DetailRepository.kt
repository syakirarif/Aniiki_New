package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import com.aniiki.features.home.ui.state.DetailUiState
import com.skydoves.sandwich.isError
import com.skydoves.sandwich.isException
import com.skydoves.sandwich.isFailure
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class DetailRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    @WorkerThread
    fun getAnimePictures(animeId: String): Flow<DetailUiState> = flow {

        val response = animeEndpoints.getAnimePictures(animeId = animeId)

        response.suspendOnSuccess {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = !this.isSuccess,
                    errorMessage = this.messageOrNull ?: "",
                    dataPictures = this.data.data ?: mutableListOf()
                )
            )
        }.suspendOnError {
//            val jsonObject = JSONObject(this.toString())
//            val errorMessage = jsonObject.getString("message")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isError,
                    errorMessage = this.message()
                )
            )
        }.suspendOnException {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isException,
                    errorMessage = this.message()
                )
            )
        }.suspendOnFailure {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isFailure,
                    errorMessage = this.message()
                )
            )
        }

    }.onStart { emit(DetailUiState(isLoading = true)) }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAnimeCharacters(animeId: String): Flow<DetailUiState> = flow {

        val response = animeEndpoints.getAnimeCharacters(animeId = animeId)

        response.suspendOnSuccess {
            Timber.e("DetailRepository | getAnimeCharacters | onSuccess | items: ${this.data.data?.size}")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = !this.isSuccess,
                    errorMessage = this.messageOrNull ?: "",
                    dataCharacters = this.data.data ?: mutableListOf()
                )
            )
        }.suspendOnError {
            Timber.e("DetailRepository | getAnimeCharacters | onError | msg: ${this.message()}")
//            val jsonObject = JSONObject(this.toString())
//            val errorMessage = jsonObject.getString("message")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isError,
                    errorMessage = this.message()
                )
            )
        }.suspendOnFailure {
            Timber.e("DetailRepository | getAnimeCharacters | onFailure | msg: ${this.message()}")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isFailure,
                    errorMessage = this.message()
                )
            )
        }.suspendOnException {
            Timber.e("DetailRepository | getAnimeCharacters | onException | msg: ${this.message()}")
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = this.isException,
                    errorMessage = this.message()
                )
            )
        }

    }.onStart { emit(DetailUiState(isLoading = true)) }.flowOn(Dispatchers.IO)
}