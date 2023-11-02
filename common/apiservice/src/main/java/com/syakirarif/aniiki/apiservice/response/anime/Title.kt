package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Title(
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("title")
    var title: String? = ""
)