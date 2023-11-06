package com.aniiki.features.login.repository

import com.syakirarif.aniiki.apiservice.datasource.LoginRDS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRDS
) : LoginRepository {
}