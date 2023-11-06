package com.syakirarif.aniiki.apiservice.api

import com.skydoves.sandwich.ApiResponse
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeEndpoints {
    @GET("seasons/2023/fall")
    suspend fun getAnimeSeason(): Response<ApiBaseResponse<List<AnimeResponse>>>

    @GET("seasons/2023/fall?limit=20&sfw&filter=tv")
    suspend fun getAnimeSeasonPaging(@Query("page") page: Int): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=airing&sfw=true&limit=10")
    suspend fun getAnimeTopAiring(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=airing&sfw=true&limit=10")
    suspend fun getAnimeTopAiringPaging(@Query("page") page: Int): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=upcoming&sfw=true&limit=10")
    suspend fun getAnimeTopUpcoming(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=upcoming&sfw=true&limit=10")
    suspend fun getAnimeTopUpcomingPaging(@Query("page") page: Int): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=bypopularity&sfw=true&limit=10")
    suspend fun getAnimeTopMostPopular(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=bypopularity&sfw=true&limit=10")
    suspend fun getAnimeTopMostPopularPaging(@Query("page") page: Int): ApiResponse<JikanBaseResponse>

//    @GET("seasons/2023/fall")
//    @PagingKeyConfig(
//        keySize = 20,
//        mapper = AnimePagingMapper::class
//    )
//    suspend fun getAnimeSeasonPaging(
//        @Query("limit") limit: Int = 20,
//        @PagingKey @Query("page") page: Int = 0
//    ): NetworkPagingSource<JikanBaseResponse, AnimeResponse>
}