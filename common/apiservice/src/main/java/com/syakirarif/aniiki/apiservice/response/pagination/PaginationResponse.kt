package com.syakirarif.aniiki.apiservice.response.pagination


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PaginationResponse(
    @SerializedName("last_visible_page")
    var lastVisiblePage: Int? = 1,
    @SerializedName("has_next_page")
    var hasNextPage: Boolean? = false,
    @SerializedName("current_page")
    var currentPage: Int? = 1,
    @SerializedName("items")
    var items: ItemsResponse? = ItemsResponse()
)