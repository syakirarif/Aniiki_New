package com.syakirarif.aniiki.apiservice.response.pagination


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PaginationResponse(
    @SerializedName("last_visible_page")
    var lastVisiblePage: Int? = 0,
    @SerializedName("has_next_page")
    var hasNextPage: Boolean? = false,
    @SerializedName("current_page")
    var currentPage: Int? = 0,
    @SerializedName("items")
    var items: ItemsResponse? = ItemsResponse()
)