package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Aired(
    @SerializedName("from")
    var from: String = "",
    @SerializedName("to")
    var to: Any = Any(),
    @SerializedName("prop")
    var prop: Prop = Prop(),
    @SerializedName("string")
    var string: String = ""
)