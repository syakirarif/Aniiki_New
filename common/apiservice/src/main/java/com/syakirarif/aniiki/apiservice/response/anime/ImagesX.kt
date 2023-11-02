package com.syakirarif.aniiki.apiservice.response.anime


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ImagesX(
    @SerializedName("image_url")
    var imageUrl: String? = "",
    @SerializedName("small_image_url")
    var smallImageUrl: String? = "",
    @SerializedName("medium_image_url")
    var mediumImageUrl: String? = "",
    @SerializedName("large_image_url")
    var largeImageUrl: String? = "",
    @SerializedName("maximum_image_url")
    var maximumImageUrl: String? = ""
)