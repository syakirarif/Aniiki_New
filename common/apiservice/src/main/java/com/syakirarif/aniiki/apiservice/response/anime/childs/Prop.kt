package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Prop(
    @SerializedName("from")
    var from: From = From(),
    @SerializedName("to")
    var to: To = To()
)