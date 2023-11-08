package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class To(
    @SerializedName("day")
    var day: Any = Any(),
    @SerializedName("month")
    var month: Any = Any(),
    @SerializedName("year")
    var year: Any = Any()
)