package com.syakirarif.aniiki.apiservice.response.character

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.childs.Images
import com.syakirarif.aniiki.apiservice.response.anime.childs.VoiceActor

@Keep
data class AnimeCharacterResponse(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("images")
    var images: Images = Images(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("name_kanji")
    var nameKanji: String = "",
    @SerializedName("nicknames")
    var nicknames: List<String> = listOf(),
    @SerializedName("favorites")
    var favorites: Int = 0,
    @SerializedName("about")
    var about: String = "",
    @SerializedName("anime")
    var anime: List<CharacterAnime> = listOf(),
    @SerializedName("voices")
    var voices: List<VoiceActor> = listOf()
)