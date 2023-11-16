package com.syakirarif.aniiki.apiservice.response.anime


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.childs.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Keep
@Parcelize
data class AnimeResponse(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("images")
    var images: @RawValue Images = Images(),
    @SerializedName("trailer")
    var trailer: @RawValue Trailer = Trailer(),
    @SerializedName("approved")
    var approved: Boolean = false,
    @SerializedName("titles")
    var titles: @RawValue List<Title> = listOf(),
    @SerializedName("title")
    var title: String = "",
    @SerializedName("title_english")
    var titleEnglish: String = "",
    @SerializedName("title_japanese")
    var titleJapanese: String = "",
    @SerializedName("title_synonyms")
    var titleSynonyms: List<String> = listOf(),
    @SerializedName("type")
    var type: String = "",
    @SerializedName("source")
    var source: String = "",
    @SerializedName("episodes")
    var episodes: Int = 0,
    @SerializedName("status")
    var status: String = "",
    @SerializedName("airing")
    var airing: Boolean = false,
    @SerializedName("aired")
    var aired: @RawValue Aired = Aired(),
    @SerializedName("duration")
    var duration: String = "",
    @SerializedName("rating")
    var rating: String = "",
    @SerializedName("score")
    var score: Double = 0.0,
    @SerializedName("scored_by")
    var scoredBy: @RawValue Any = Any(),
    @SerializedName("rank")
    var rank: @RawValue Any = Any(),
    @SerializedName("popularity")
    var popularity: Int = 0,
    @SerializedName("members")
    var members: Int = 0,
    @SerializedName("favorites")
    var favorites: Int = 0,
    @SerializedName("synopsis")
    var synopsis: String = "",
    @SerializedName("background")
    var background: String = "",
    @SerializedName("season")
    var season: String = "",
    @SerializedName("year")
    var year: Int = 0,
    @SerializedName("broadcast")
    var broadcast: @RawValue Broadcast = Broadcast(),
    @SerializedName("producers")
    var producers: @RawValue List<Producer> = listOf(),
    @SerializedName("licensors")
    var licensors: @RawValue List<Any> = listOf(),
    @SerializedName("studios")
    var studios: @RawValue List<Studio> = listOf(),
    @SerializedName("genres")
    var genres: @RawValue List<Genre> = listOf(),
    @SerializedName("explicit_genres")
    var explicitGenres: @RawValue List<Any> = listOf(),
    @SerializedName("themes")
    var themes: @RawValue List<Theme> = listOf(),
    @SerializedName("demographics")
    var demographics: @RawValue List<Demographic> = listOf(),
    var favourite: Boolean = false
) : Parcelable