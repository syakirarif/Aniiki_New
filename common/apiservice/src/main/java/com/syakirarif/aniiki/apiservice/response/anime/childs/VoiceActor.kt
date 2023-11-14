package com.syakirarif.aniiki.apiservice.response.anime.childs

import com.google.gson.annotations.SerializedName

data class VoiceActor(
    @SerializedName("person")
    var person: Person = Person(),
    @SerializedName("language")
    var language: String = ""
)
