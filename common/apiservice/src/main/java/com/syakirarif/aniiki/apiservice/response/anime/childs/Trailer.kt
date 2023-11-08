package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Trailer(
    @SerializedName("youtube_id")
    var youtubeId: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("embed_url")
    var embedUrl: String = "",
    @SerializedName("images")
    var images: ImagesX = ImagesX()
)