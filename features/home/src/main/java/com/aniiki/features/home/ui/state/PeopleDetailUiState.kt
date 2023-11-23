package com.aniiki.features.home.ui.state

import com.syakirarif.aniiki.apiservice.response.anime.childs.Person
import com.syakirarif.aniiki.apiservice.response.character.AnimeCharacterResponse
import com.syakirarif.aniiki.apiservice.response.people.PeopleResponse

data class PeopleDetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dataCharacterDetail: AnimeCharacterResponse = AnimeCharacterResponse(),
    val dataPeopleDetail: PeopleResponse = PeopleResponse(),
    val dataPeopleVoicedCharacters: List<Person> = listOf(Person())
)