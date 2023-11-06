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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<AnimeResponse> = mutableListOf(),
    val dataPaging: PagingData<AnimeResponse>? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

//    val animeTopAiring2 = homeRepository.getAnimeTopAiring2()

    private val _animeSeasonState = MutableStateFlow(HomeUiState())
    val animeSeasonState: StateFlow<HomeUiState> = _animeSeasonState.asStateFlow()

    val homeUiState: StateFlow<HomeUiState> = homeRepository.getAnimeTopAiring2().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = HomeUiState(isLoading = true)
    )

    val animeTopUpcomingState: StateFlow<HomeUiState> =
        homeRepository.getAnimeTopUpcoming().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true)
        )

    val animeTopMostPopularState: StateFlow<HomeUiState> =
        homeRepository.getAnimeTopMostPopular().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true)
        )

//    fun fetchAnimeTopAiring2(){
//        viewModelScope.launch {
//            _homeUiState.value = homeRepository.getAnimeTopAiring2().stateIn(
//    scope = viewModelScope,
//    started = SharingStarted.WhileSubscribed(5000L),
//    initialValue = HomeUiState(isLoading = true)
//    ).value
//        }
//    }

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


    private var _animePaging = MutableStateFlow(HomeUiState())

    //    private var _animePaging = mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animePaging: StateFlow<HomeUiState> get() = _animePaging.asStateFlow()

    private var _animeTopAiringPaging = mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animeTopAiringPaging: State<Flow<PagingData<AnimeResponse>>> get() = _animeTopAiringPaging

    private var _animeTopUpcomingPaging =
        mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animeTopUpcomingPaging: State<Flow<PagingData<AnimeResponse>>> get() = _animeTopUpcomingPaging

//    private var _animeTopAiring = mutableStateOf<MutableList<AnimeResponse>>(mutableListOf())
//    val animeTopAiring: State<List<AnimeResponse>> get() = _animeTopAiring

    private var _animeTopMostPopularPaging =
        mutableStateOf<Flow<PagingData<AnimeResponse>>>(emptyFlow())
    val animeTopMostPopularPaging: State<Flow<PagingData<AnimeResponse>>> get() = _animeTopMostPopularPaging

//    private var _errorMessage = mutableStateOf<String>("")
//    val errorMessage: State<String> get() = _errorMessage

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            animeFlow.collectLatest {
//                anime.value.addAll(it)
//            }
//        }
        fetchAnimePaging()
//        fetchAnimeTopAiringPaging()
//        fetchAnimeTopUpcomingPaging()
//        fetchAnimeTopMostPopularPaging()
//        fetchAnimeTopAiring2()
    }

    fun fetchAnimePaging() {
        viewModelScope.launch {
            val result = homeRepository.getAnimeListPaging(onError = {
//                _errorMessage.value = it
                _animeSeasonState.value = HomeUiState(errorMessage = it)
            }).cachedIn(viewModelScope)
            _animePaging.value = HomeUiState(dataPaging = result)
        }
    }

//    private val _isLoading: MutableState<Boolean> = mutableStateOf(false)
//    val isLoading: State<Boolean> get() = _isLoading

//    val animeTopAiring: Flow<List<AnimeResponse>> =
//        homeRepository.getAnimeTopAiring(
//            onStart = {
////                _isLoading.value = true
//                _homeUiState.value = HomeUiState(isLoading = true)
//                      },
//            onError = {
////                _errorMessage.value = it
//                _homeUiState.value = HomeUiState(isLoading = false, isError = true, errorMessage = it)
//                      },
//            onCompletion = {
////                _isLoading.value = false
//                _homeUiState.value = HomeUiState(isLoading = false)
//            },
//            onSuccess = {
////                _isLoading.value = false
//                Timber.e("animeTopAiring - onSuccess => ${it.size}")
//                _homeUiState.value = HomeUiState(isLoading = false, isError = false)
//            }
//        )

    fun fetchAnimeTopAiring() {
////        homeRepository.getAnimeTopAiring(
////            onStart = { HomeUiState.Loading },
////            onError = { HomeUiState.Error(it) },
////            onSuccess = { HomeUiState.Complete(it) }
////        )
//        viewModelScope.launch {
//            val test = homeRepository.getAnimeTopAiring()
//            _homeUiState.value = test.first()
//            homeRepository.getAnimeTopAiring(
//                onStart = {
//                    _isLoading.value = true
//                    _homeUiState.value = HomeUiState(isLoading = true)
//                },
//                onError = {
//                    _errorMessage.value = it
//                    _homeUiState.value = HomeUiState(isLoading = false, isError = true, errorMessage = it)
//                },
//                onCompletion = {
//                    _isLoading.value = false
//                    _homeUiState.value = HomeUiState(isLoading = false)
//                },
//                onSuccess = {
//                    _isLoading.value = false
//                    Timber.e("animeTopAiring - onSuccess => ${it.size}")
//                    _homeUiState.value = HomeUiState(isLoading = false, isError = false, data = it)
//                }
//            ).stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = mutableListOf()
//            )
//        }
    }

//    fun fetchAnimeTopAiring() {
//        viewModelScope.launch {
//            homeRepository.getAnimeTopAiring(
//                onSuccess = {},
//                onError = {}
//            ).also {
//
//            }
//        }
////        _animeTopAiring.value =
////            homeRepository.getAnimeTopAiring(
////                onSuccess = {},
////                onError = {}
////            )
//
//    }

    fun fetchAnimeTopAiringPaging() {
        viewModelScope.launch {
            _animeTopAiringPaging.value =
                homeRepository.getAnimeTopAiringPaging().cachedIn(viewModelScope)
        }
    }

    fun fetchAnimeTopUpcomingPaging() {
        viewModelScope.launch {
            _animeTopUpcomingPaging.value =
                homeRepository.getAnimeTopUpcomingPaging().cachedIn(viewModelScope)
        }
    }

    fun fetchAnimeTopMostPopularPaging() {
        viewModelScope.launch {
            _animeTopMostPopularPaging.value =
                homeRepository.getAnimeTopMostPopularPaging().cachedIn(viewModelScope)
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