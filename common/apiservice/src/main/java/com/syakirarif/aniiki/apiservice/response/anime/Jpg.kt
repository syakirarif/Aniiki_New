package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Jpg(
    @SerializedName("image_url")
    var imageUrl: String? = "",
    @SerializedName("small_image_url")
    var smallImageUrl: String? = "",
    @SerializedName("large_image_url")
    var largeImageUrl: String? = ""
)