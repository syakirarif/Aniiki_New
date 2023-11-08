package com.syakirarif.aniiki.apiservice.response.anime.childs


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Relation(
    @SerializedName("relation")
    var relation: String = "",
    @SerializedName("entry")
    var entry: List<Entry> = listOf()
)