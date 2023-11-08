package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Images(
    @SerializedName("jpg")
    var jpg: Jpg = Jpg(),
    @SerializedName("webp")
    var webp: Webp = Webp()
)