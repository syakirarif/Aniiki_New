package com.aniiki.features.home.repository

import androidx.annotation.WorkerThread
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints

class PeopleDetailRepository constructor(
    private val animeEndpoints: AnimeEndpoints
) {
    @WorkerThread
    fun getCharacterDetail(charId: String) {

    }
}