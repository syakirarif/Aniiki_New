package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Title(
    @SerializedName("type")
    var type: String = "",
    @SerializedName("title")
    var title: String = ""
)