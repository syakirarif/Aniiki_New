package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Broadcast(
    @SerializedName("day")
    var day: String? = "",
    @SerializedName("time")
    var time: String? = "",
    @SerializedName("timezone")
    var timezone: String? = "",
    @SerializedName("string")
    var string: String? = ""
)