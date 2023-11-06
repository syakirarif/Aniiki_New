package com.syakirarif.aniiki.apiservice.datasource

import android.annotation.SuppressLint
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
@SuppressLint("CheckResult")
class LoginRDS @Inject constructor(
    private val animeEndpoints: AnimeEndpoints
) {

}