package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ThemeX(
    @SerializedName("openings")
    var openings: List<String> = listOf(),
    @SerializedName("endings")
    var endings: List<String> = listOf()
)