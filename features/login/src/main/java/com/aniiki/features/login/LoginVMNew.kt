package com.aniiki.features.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aniiki.features.login.usecase.LoginUseCase
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LoginVMNew @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {

    private val _loadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
    val loadingState: State<NetworkState> get() = _loadingState

    val anime: State<MutableList<AnimeResponse>> = mutableStateOf(mutableListOf())

    val animePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

    var canPaginate: MutableState<Boolean> = mutableStateOf(true)

    val animeList: Flow<List<AnimeResponse>?> =
        useCase.getAnimeList(
            page = 1,
            onStart = { _loadingState.value = NetworkState.LOADING },
            onCompletion = {
                _loadingState.value = NetworkState.SUCCESS
//                fetchNextPage()
            },
            onError = { _loadingState.value = NetworkState.ERROR }
        )

    private val animeSeasonFlow = animePageStateFlow.flatMapLatest {

        useCase.getAnimeSeason2(
            page = it,
            success = {
                _loadingState.value = NetworkState.SUCCESS
//                fetchNextPage()
            },
            error = { _loadingState.value = NetworkState.ERROR },
            start = { _loadingState.value = NetworkState.LOADING }
        )
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        fetchAnimeSeason()
    }

    fun fetchAnimeSeason() {
        viewModelScope.launch(Dispatchers.IO) {
            animeSeasonFlow.collectLatest {
                it?.let {
                    anime.value.addAll(it)
                }
            }
        }
    }

    fun fetchNextPage() {
        if (loadingState.value != NetworkState.LOADING && animePageStateFlow.value < 11) {
            animePageStateFlow.value++
            canPaginate.value = true
        } else {
            canPaginate.value = false
        }
    }

    override fun onCleared() {
        animePageStateFlow.value = 1
        _loadingState.value = NetworkState.IDLE
        canPaginate.value = true
        super.onCleared()
    }
}