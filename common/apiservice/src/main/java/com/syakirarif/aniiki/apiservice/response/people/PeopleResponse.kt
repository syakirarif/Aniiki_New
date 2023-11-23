package com.syakirarif.aniiki.apiservice.response.people

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.childs.Images
import com.syakirarif.aniiki.apiservice.response.character.CharacterAnime

@Keep
data class PeopleResponse(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("website_url")
    var websiteUrl: String = "",
    @SerializedName("images")
    var images: Images = Images(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("given_name")
    var givenName: String = "",
    @SerializedName("family_name")
    var familyName: String = "",
    @SerializedName("nicknames")
    var nicknames: List<String> = listOf(),
    @SerializedName("birthday")
    var birthday: String = "",
    @SerializedName("favorites")
    var favorites: Int = 0,
    @SerializedName("about")
    var about: String = "",
    @SerializedName("anime")
    var anime: List<CharacterAnime> = listOf(),
    @SerializedName("voices")
    var voices: List<PeopleVoice> = listOf()
)