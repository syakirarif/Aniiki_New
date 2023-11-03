package com.aniiki.features.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.suspendOnSuccess
import com.syakirarif.aniiki.apiservice.api.AnimeEndpoints
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.pagination.PaginationResponse
import com.syakirarif.aniiki.core.utils.debugPrint
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class HomePagingSource(
    private val animeEndpoints: AnimeEndpoints,
    private val onError: (String) -> Unit
) :
    PagingSource<Int, AnimeResponse>() {
    override fun getRefreshKey(state: PagingState<Int, AnimeResponse>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeResponse> {
        try {
            val nextPage = params.key ?: 1
            debugPrint("nextPage => $nextPage")
            val animes: MutableList<AnimeResponse> = mutableListOf()

            val response = animeEndpoints.getAnimeSeasonPaging(nextPage)
            var pagination = PaginationResponse()
            response.suspendOnSuccess {
                animes.addAll(data.data)
                if (data.pagination != null)
                    pagination = data.pagination!!
            }.onError {
                val jsonObject = JSONObject(this.toString())
                onError(jsonObject.getString("error"))
            }

            return LoadResult.Page(
                data = animes,
//                prevKey = if (pagination.currentPage!! == 1) null else pagination.currentPage!! - 1,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (!pagination.hasNextPage!!) null else pagination.currentPage!! + 1
            )

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}