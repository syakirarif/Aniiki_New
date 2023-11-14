package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Person(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("images")
    var images: Images = Images(),
)