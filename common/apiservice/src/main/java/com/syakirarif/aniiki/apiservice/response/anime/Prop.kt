package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Prop(
    @SerializedName("from")
    var from: From? = From(),
    @SerializedName("to")
    var to: To? = To()
)