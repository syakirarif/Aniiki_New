package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImagesX(
    @SerializedName("image_url")
    var imageUrl: String = "",
    @SerializedName("small_image_url")
    var smallImageUrl: String = "",
    @SerializedName("medium_image_url")
    var mediumImageUrl: String = "",
    @SerializedName("large_image_url")
    var largeImageUrl: String = "",
    @SerializedName("maximum_image_url")
    var maximumImageUrl: String = ""
)