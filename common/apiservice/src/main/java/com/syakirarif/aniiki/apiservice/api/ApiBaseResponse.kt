package com.syakirarif.aniiki.apiservice.api

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.pagination.PaginationResponse

@Immutable
@Keep
data class ApiBaseResponse<T>(
    @SerializedName("data")
    var data: T? = null,
    @SerializedName("pagination")
    var pagination: PaginationResponse? = PaginationResponse()
)