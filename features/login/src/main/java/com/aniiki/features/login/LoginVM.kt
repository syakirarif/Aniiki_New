package com.aniiki.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aniiki.features.login.usecase.LoginUseCase
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class LoginUiState(
    val data: List<AnimeResponse> = listOf(),
    val isLoading: Boolean = false
)

@HiltViewModel
class LoginVM @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {
//    private val _uiState = MutableStateFlow(LoginUiState())
//
//    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
//
//    init {
//        getAnimeSeason()
//    }
//
//    private fun getAnimeSeason() {
//        _uiState.update { it.copy(isLoading = true) }
//        viewModelScope.launch {
//            val result = useCase.getAnimeSeason().first()
//            result.let {
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        data = it.data
//                    )
//                }
//            }
//        }
//
//    }

//    init {
//        getAnimeSeason()
//    }
//
//    private val _data: MutableState<Resource<List<AnimeResponse>>> = mutableStateOf(Resource())
//    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
//    val data: State<Resource<List<AnimeResponse>>> = _data
//    val isLoading: State<Boolean> = _isLoading
//
//    private fun getAnimeSeason() {
////        _isLoading.value = true
//        viewModelScope.launch {
//            val result = useCase.getAnimeSeason().first()
//            result.let {
////                _isLoading.value = false
//                _data.value = it
//            }
//        }
//    }
//
//    private val _books = MutableLiveData<Resource<List<AnimeResponse>>>()
//    val books : LiveData<Resource<List<AnimeResponse>>> get() = _books
//
//    fun getAnimeSeason2() = useCase.getAnimeSeason().asLiveData()
//
//    fun getAnimeSeason3() {
//        viewModelScope.launch {
//            _books.value = useCase.getAnimeSeason().first()
//        }
//    }

//    private val _animes = MutableLiveData<Resource<List<AnimeResponse>>>()
//    val animes: LiveData<Resource<List<AnimeResponse>>> get() = _animes
//
//    init {
//        getAnimeList()
//        Timber.e("INIT VIEWMODEL")
//    }
//
//    fun getAnimeList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            val result = useCase.getAnimeSeason().asLiveData()
//            _animes.postValue(result.value)
//        }
//    }

    fun getAnimeAsLiveData() = useCase.getAnimeSeason().asLiveData()

}