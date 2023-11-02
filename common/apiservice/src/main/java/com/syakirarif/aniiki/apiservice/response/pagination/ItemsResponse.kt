package com.syakirarif.aniiki.apiservice.response.pagination


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ItemsResponse(
    @SerializedName("count")
    var count: Int? = 0,
    @SerializedName("total")
    var total: Int? = 0,
    @SerializedName("per_page")
    var perPage: Int? = 0
)