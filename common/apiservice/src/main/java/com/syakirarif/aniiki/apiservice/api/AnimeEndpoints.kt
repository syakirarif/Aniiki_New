package com.syakirarif.aniiki.apiservice.api

import com.skydoves.sandwich.ApiResponse
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.anime.childs.Character
import com.syakirarif.aniiki.apiservice.response.anime.childs.Images
import com.syakirarif.aniiki.apiservice.response.character.AnimeCharacterResponse
import com.syakirarif.aniiki.apiservice.response.people.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val DEFAULT_PAGE_SIZE = 25

interface AnimeEndpoints {
    @GET("seasons/2023/fall")
    suspend fun getAnimeSeason(): Response<ApiBaseResponse<List<AnimeResponse>>>

    //    @GET("seasons/2023/fall?limit=20&sfw&filter=tv")
    @GET("seasons/{year}/{season}?sfw&filter=tv")
    suspend fun getAnimeSeasonPaging(
        @Path(value = "year", encoded = true) year: String,
        @Path(value = "season", encoded = true) season: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE
    ): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=airing&sfw=true&limit=10")
    suspend fun getAnimeTopAiring(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=airing&sfw=true")
    suspend fun getAnimeTopAiringPaging(
        @Query("page") page: Int,
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE
    ): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=upcoming&sfw=true&limit=10")
    suspend fun getAnimeTopUpcoming(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=upcoming&sfw=true")
    suspend fun getAnimeTopUpcomingPaging(
        @Query("page") page: Int,
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE
    ): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=bypopularity&sfw=true&limit=10")
    suspend fun getAnimeTopMostPopular(): ApiResponse<JikanBaseResponse>

    @GET("top/anime?type=tv&filter=bypopularity&sfw=true")
    suspend fun getAnimeTopMostPopularPaging(
        @Query("page") page: Int,
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE
    ): ApiResponse<JikanBaseResponse>

    @GET("schedules?sfw=true")
    suspend fun getAnimeSchedulePaging(
        @Query("page") page: Int,
        @Query("limit") limit: Int? = DEFAULT_PAGE_SIZE,
        @Query("filter") day: String
    ): ApiResponse<JikanBaseResponse>

    @GET("schedules?sfw=true")
    suspend fun getAnimeSchedule(
        @Query("filter") day: String
    ): ApiResponse<JikanBaseResponse>

    @GET("anime/{anime_id}/pictures")
    suspend fun getAnimePictures(
        @Path(value = "anime_id", encoded = true) animeId: String
    ): ApiResponse<JikanBaseResponseGeneric<List<Images>>>

    @GET("anime/{anime_id}/characters")
    suspend fun getAnimeCharacters(
        @Path(value = "anime_id", encoded = true) animeId: String
    ): ApiResponse<JikanBaseResponseGeneric<List<Character>>>

    @GET("characters/{char_id}/full")
    suspend fun getCharacterDetail(
        @Path(value = "char_id", encoded = true) charId: String
    ): ApiResponse<JikanBaseResponseGeneric<AnimeCharacterResponse>>

    @GET("people/{mal_id}/full")
    suspend fun getPeopleDetail(
        @Path(value = "mal_id", encoded = true) malId: String
    ): ApiResponse<JikanBaseResponseGeneric<PeopleResponse>>
}