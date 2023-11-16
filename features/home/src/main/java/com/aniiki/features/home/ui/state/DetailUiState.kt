package com.aniiki.features.home.ui.state

import com.syakirarif.aniiki.apiservice.response.anime.childs.Character
import com.syakirarif.aniiki.apiservice.response.anime.childs.Images

data class DetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dataPictures: List<Images> = mutableListOf(Images()),
    val dataCharacters: List<Character> = mutableListOf(Character()),
)
