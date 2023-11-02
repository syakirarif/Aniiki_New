package com.aniiki.features.home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aniiki.features.home.repository.HomeRepository
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

//    private val _loadingState: MutableState<NetworkState> = mutableStateOf(NetworkState.IDLE)
//    val loadingState: State<NetworkState> get() = _loadingState
//
//    val anime: State<MutableList<AnimeResponse>> = mutableStateOf(mutableListOf())
//
//    val animePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)

//    private val animeFlow = animePageStateFlow.flatMapLatest {
//        homeRepository.getAnimeList(
//            page = it,
//            onCompletion = { _loadingState.value = NetworkState.SUCCESS },
//            onError = { _loadingState.value = NetworkState.ERROR },
//            onStart = { _loadingState.value = NetworkState.LOADING }
//        )
//    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)


    private var _animePaging = mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animePaging: State<Flow<PagingData<AnimeResponse>>> get() = _animePaging

    private var _animeTopAiringPaging = mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animeTopAiringPaging: State<Flow<PagingData<AnimeResponse>>> get() = _animeTopAiringPaging

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            animeFlow.collectLatest {
//                anime.value.addAll(it)
//            }
//        }
        fetchAnimePaging()
        fetchAnimeTopAiringPaging()
    }

    fun fetchAnimePaging() {
        viewModelScope.launch {
            _animePaging.value = homeRepository.getAnimeListPaging().cachedIn(viewModelScope)
        }
    }

    fun fetchAnimeTopAiringPaging() {
        viewModelScope.launch {
            _animeTopAiringPaging.value =
                homeRepository.getAnimeTopAiringPaging().cachedIn(viewModelScope)
        }
    }

//    fun fetchAnimePaging2() {
//        viewModelScope.launch {
//            val pagingSource = homeRepository.getAnimeListPaging2()
//            val pagerFlow = Pager(PagingConfig(pageSize = 20)) { pagingSource }.flow
//            _animePaging.value = pagerFlow
//        }
//    }

//    fun fetchAnimeNextPage() {
//        if (loadingState.value != NetworkState.LOADING) {
//            animePageStateFlow.value++
//        }
//    }


}