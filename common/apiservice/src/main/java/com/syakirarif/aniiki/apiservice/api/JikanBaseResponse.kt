package com.syakirarif.aniiki.apiservice.api

import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.pagination.PaginationResponse
import androidx.compose.runtime.Immutable
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse

@Immutable
data class JikanBaseResponse(
    @SerializedName("data")
    var data: List<AnimeResponse> = listOf(),
    @SerializedName("pagination")
    var pagination: PaginationResponse? = PaginationResponse()
) : JikanNetworkModel
