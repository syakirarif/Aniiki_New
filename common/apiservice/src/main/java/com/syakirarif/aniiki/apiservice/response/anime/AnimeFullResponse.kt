package com.syakirarif.aniiki.apiservice.response.anime


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.syakirarif.aniiki.apiservice.response.anime.childs.*

@Keep
data class AnimeFullResponse(
    @SerializedName("mal_id")
    var malId: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @SerializedName("images")
    var images: Images = Images(),
    @SerializedName("trailer")
    var trailer: Trailer = Trailer(),
    @SerializedName("approved")
    var approved: Boolean = false,
    @SerializedName("titles")
    var titles: List<Title> = listOf(),
    @SerializedName("title")
    var title: String = "",
    @SerializedName("title_english")
    var titleEnglish: Any = Any(),
    @SerializedName("title_japanese")
    var titleJapanese: String = "",
    @SerializedName("title_synonyms")
    var titleSynonyms: List<Any> = listOf(),
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
    var aired: Aired = Aired(),
    @SerializedName("duration")
    var duration: String = "",
    @SerializedName("rating")
    var rating: String = "",
    @SerializedName("score")
    var score: Double = 0.0,
    @SerializedName("scored_by")
    var scoredBy: Int = 0,
    @SerializedName("rank")
    var rank: Int = 0,
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
    var broadcast: Broadcast = Broadcast(),
    @SerializedName("producers")
    var producers: List<Producer> = listOf(),
    @SerializedName("licensors")
    var licensors: List<Any> = listOf(),
    @SerializedName("studios")
    var studios: List<Studio> = listOf(),
    @SerializedName("genres")
    var genres: List<Genre> = listOf(),
    @SerializedName("explicit_genres")
    var explicitGenres: List<Any> = listOf(),
    @SerializedName("themes")
    var themes: List<Theme> = listOf(),
    @SerializedName("demographics")
    var demographics: List<Demographic> = listOf(),
    @SerializedName("relations")
    var relations: List<Relation> = listOf(),
    @SerializedName("theme")
    var theme: ThemeX = ThemeX(),
    @SerializedName("external")
    var `external`: List<External> = listOf(),
    @SerializedName("streaming")
    var streaming: List<Streaming> = listOf()
)