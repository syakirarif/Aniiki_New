package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class External(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("url")
    var url: String = ""
)