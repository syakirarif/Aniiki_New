package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class From(
    @SerializedName("day")
    var day: Int? = 0,
    @SerializedName("month")
    var month: Int? = 0,
    @SerializedName("year")
    var year: Int? = 0
)