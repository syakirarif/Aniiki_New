package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Demographic(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("type")
    var type: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("url")
    var url: String = ""
)