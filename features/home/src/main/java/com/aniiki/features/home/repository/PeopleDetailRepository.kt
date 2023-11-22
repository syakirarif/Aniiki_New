package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import com.aniiki.features.home.repository.utils.unsuccessfulPeopleDetailUiState
import com.aniiki.features.home.ui.state.PeopleDetailUiState
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.message
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.character.AnimeCharacterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class PeopleDetailRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    @WorkerThread
    fun getCharacterDetail(charId: String): Flow<PeopleDetailUiState> = flow {

        val response = animeEndpoints.getCharacterDetail(charId = charId)

        response.suspendOnSuccess {
            Timber.e("PeopleDetailRepository | getCharacterDetail | onSuccess | items: ${this.data.data?.name}")
            emit(
                PeopleDetailUiState(
                    isLoading = false,
                    isError = !this.isSuccess,
                    errorMessage = this.messageOrNull ?: "",
                    dataCharacterDetail = this.data.data ?: AnimeCharacterResponse()
                )
            )
        }.suspendOnError {
            Timber.e("PeopleDetailRepository | getCharacterDetail | onError | msg: ${this.message()}")
            emit(
                unsuccessfulPeopleDetailUiState(this.message())
            )
        }.suspendOnFailure {
            Timber.e("PeopleDetailRepository | getCharacterDetail | onFailure | msg: ${this.message()}")
            emit(
                unsuccessfulPeopleDetailUiState(this.message())
            )
        }
    }.onStart { emit(PeopleDetailUiState(isLoading = true)) }.flowOn(Dispatchers.IO)
}