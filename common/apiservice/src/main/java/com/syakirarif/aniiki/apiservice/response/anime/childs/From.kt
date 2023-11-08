package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class From(
    @SerializedName("day")
    var day: Int = 0,
    @SerializedName("month")
    var month: Int = 0,
    @SerializedName("year")
    var year: Int = 0
)