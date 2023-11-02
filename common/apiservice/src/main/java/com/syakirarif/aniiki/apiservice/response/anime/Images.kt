package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Images(
    @SerializedName("jpg")
    var jpg: Jpg? = Jpg(),
    @SerializedName("webp")
    var webp: Webp? = Webp()
)