package com.aniiki.features.home.repository

import androidx.paging.PagingData
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.core.base.createPager
import com.syakirarif.aniiki.core.utils.getCurrentDay
import kotlinx.coroutines.flow.Flow

class ScheduleRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    fun getAnimeSchedulePaging(onError: (String) -> Unit): Flow<PagingData<AnimeResponse>> {
        return createPager(
            enablePlaceholders = true,
            onError = onError
        ) { page ->
            animeEndpoints.getAnimeSchedule(
                page = page,
                day = getCurrentDay().lowercase()
            )
        }.flow
    }
}