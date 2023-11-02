package com.syakirarif.aniiki.apiservice.paging

import com.skydoves.retrofit.adapters.paging.PagingMapper
import com.syakirarif.aniiki.apiservice.api.JikanBaseResponse
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse

class AnimePagingMapper : PagingMapper<JikanBaseResponse, AnimeResponse> {
    override fun map(value: JikanBaseResponse): List<AnimeResponse> {
        return value.data
    }
}