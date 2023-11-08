package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Webp(
    @SerializedName("image_url")
    var imageUrl: String = "",
    @SerializedName("small_image_url")
    var smallImageUrl: String = "",
    @SerializedName("large_image_url")
    var largeImageUrl: String = ""
)