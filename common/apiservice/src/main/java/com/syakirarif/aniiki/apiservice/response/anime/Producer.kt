package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Producer(
    @SerializedName("mal_id")
    var malId: Int? = 0,
    @SerializedName("type")
    var type: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("url")
    var url: String? = ""
)