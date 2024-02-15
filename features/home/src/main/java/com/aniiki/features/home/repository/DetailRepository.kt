package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import com.aniiki.features.home.repository.utils.unsuccessfulDetailUiState
import com.aniiki.features.home.ui.state.DetailUiState
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.utils.ErrorEnvelopeMapper
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
        }.suspendOnError(ErrorEnvelopeMapper) {
//            val jsonObject = JSONObject(this.toString())
//            val errorMessage = jsonObject.getString("message")
            emit(
                unsuccessfulDetailUiState(message = this.codeMessage)
            )
        }.suspendOnFailure {
            emit(
                unsuccessfulDetailUiState(message = this.message())
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
        }.suspendOnError(ErrorEnvelopeMapper) {
            Timber.e("DetailRepository | getAnimeCharacters | onError | msg: ${this.codeMessage}")
//            val jsonObject = JSONObject(this.toString())
//            val errorMessage = jsonObject.getString("message")
            emit(
                unsuccessfulDetailUiState(message = this.codeMessage)
            )
        }.suspendOnFailure {
            Timber.e("DetailRepository | getAnimeCharacters | onFailure | msg: ${this.message()}")
            emit(
                unsuccessfulDetailUiState(message = this.message())
            )
        }

    }.onStart { emit(DetailUiState(isLoading = true)) }.flowOn(Dispatchers.IO)
}