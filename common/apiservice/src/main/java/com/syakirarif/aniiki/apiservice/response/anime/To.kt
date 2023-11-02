package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class To(
    @SerializedName("day")
    var day: Any? = Any(),
    @SerializedName("month")
    var month: Any? = Any(),
    @SerializedName("year")
    var year: Any? = Any()
)