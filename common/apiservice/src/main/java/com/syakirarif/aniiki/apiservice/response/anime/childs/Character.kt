package com.syakirarif.aniiki.apiservice.response.anime.childs

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("character")
    var character: Person = Person(),
    @SerializedName("role")
    var role: String = "",
    @SerializedName("favorites")
    var favorites: Int = 0,
    @SerializedName("voice_actors")
    var voiceActors: List<VoiceActor> = mutableListOf(VoiceActor()),
//    var favourite: Boolean = false
)
