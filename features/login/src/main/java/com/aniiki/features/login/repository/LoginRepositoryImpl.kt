package com.aniiki.features.login.repository

import com.syakirarif.aniiki.apiservice.datasource.LoginRDS
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.utils.ApiResponse
import com.syakirarif.aniiki.core.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class LoginRepositoryImpl @Inject constructor(
    private val remoteDataSource: LoginRDS
) : LoginRepository {
    override fun getAnimeSeason(): Flow<Resource<List<AnimeResponse>>> {
        return channelFlow {
            send(Resource.loading())
            when (val request = remoteDataSource.getAnimeSeason().first()) {
                is ApiResponse.Success -> {
                    send(Resource.success(request.message, request.data))
                }

                is ApiResponse.Empty -> {
                    send(Resource.empty(request.errorMessage))
                }

                is ApiResponse.Failed -> {
                    send(Resource.failed(msg = request.errorMessage, errCode = request.errCode))
                }

                is ApiResponse.Error -> {
                    send(Resource.error(request.errorMessage, null))
                }

                else -> {
                    send(Resource.error("Error occured while fetching data", null))
                }
            }
        }
    }

    override fun getAnimeSeason2(
        page: Int,
        success: () -> Unit,
        error: () -> Unit,
        start: () -> Unit
    ): Flow<List<AnimeResponse>?> = remoteDataSource.getAnimeSeason2(page, success, error, start)

    override fun getAnimeList(
        page: Int,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ): Flow<List<AnimeResponse>?> = remoteDataSource.getAnimeList(page, onStart, onCompletion, onError)
}