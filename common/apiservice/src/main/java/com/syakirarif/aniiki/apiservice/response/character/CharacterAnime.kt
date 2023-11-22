package com.syakirarif.aniiki.apiservice.response.character

import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse

data class CharacterAnime(
    @SerializedName("role")
    var role: String = "",
    @SerializedName("anime")
    var anime: AnimeResponse = AnimeResponse()
)
