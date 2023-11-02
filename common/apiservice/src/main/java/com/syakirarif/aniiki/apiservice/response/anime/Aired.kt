package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Aired(
    @SerializedName("from")
    var from: String? = "",
    @SerializedName("to")
    var to: Any? = Any(),
    @SerializedName("prop")
    var prop: Prop? = Prop(),
    @SerializedName("string")
    var string: String? = ""
)