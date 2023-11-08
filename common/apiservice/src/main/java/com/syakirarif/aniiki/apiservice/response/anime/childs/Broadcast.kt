package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Broadcast(
    @SerializedName("day")
    var day: String = "",
    @SerializedName("time")
    var time: String = "",
    @SerializedName("timezone")
    var timezone: String = "",
    @SerializedName("string")
    var string: String = ""
)