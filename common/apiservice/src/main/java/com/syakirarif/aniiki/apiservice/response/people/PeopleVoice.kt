package com.syakirarif.aniiki.apiservice.response.people

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.anime.childs.Person

@Keep
data class PeopleVoice(
    @SerializedName("role")
    var role: String = "",
    @SerializedName("anime")
    var anime: AnimeResponse = AnimeResponse(),
    @SerializedName("character")
    var character: Person = Person()
)
