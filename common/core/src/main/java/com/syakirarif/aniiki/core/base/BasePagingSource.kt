package com.syakirarif.aniiki.core.base

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.syakirarif.aniiki.apiservice.api.DEFAULT_PAGE_SIZE
import com.syakirarif.aniiki.apiservice.api.JikanBaseResponse
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.pagination.PaginationResponse
import org.json.JSONObject

class BasePagingSource(
//    private val totalPages: Int? = null,
//    private val block: suspend (Int) -> List<V>
    private val block: suspend (Int) -> ApiResponse<JikanBaseResponse>,
    private val onError: (String) -> Unit
) : PagingSource<Int, AnimeResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeResponse> {
        val page = params.key ?: 1
        return try {
            val animes: MutableList<AnimeResponse> = mutableListOf()
            val response = block(page)
            var pagination: PaginationResponse? = PaginationResponse()
            var totalPages: Int? = pagination?.lastVisiblePage
            var hasNextPage: Boolean? = pagination?.hasNextPage

            response.onSuccess {
                animes.addAll(this.data.data)
                pagination = data.pagination
                totalPages = pagination?.lastVisiblePage
                hasNextPage = pagination?.hasNextPage
            }.onError {
                val jsonObject = JSONObject(this.toString())
                onError(jsonObject.getString("error"))
            }

            val nextKey = if (animes.isEmpty()) {
                null
            } else {
                if (totalPages != null) {
                    if (page > totalPages!!) {
                        null
                    } else {
                        if (hasNextPage != null && hasNextPage!!) {
                            page + 1
                        } else {
                            null
                        }
                    }
                } else {
                    null
                }

            }

            LoadResult.Page(
                data = animes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
//                nextKey = if (!pagination.hasNextPage!!) null else pagination.currentPage!! + 1
//                nextKey = if (page == totalPages && !hasNextPage!!) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

fun createPager(
    pageSize: Int = DEFAULT_PAGE_SIZE,
    enablePlaceholders: Boolean = false,
    onError: (String) -> Unit,
    block: suspend (Int) -> ApiResponse<JikanBaseResponse>
): Pager<Int, AnimeResponse> = Pager(
    config = PagingConfig(enablePlaceholders = enablePlaceholders, pageSize = pageSize),
    pagingSourceFactory = { BasePagingSource(block, onError) }
)