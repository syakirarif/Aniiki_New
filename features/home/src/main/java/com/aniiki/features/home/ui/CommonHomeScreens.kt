package com.aniiki.features.home.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@Composable
fun AnimeGridListPaging(
    pagingItems: LazyPagingItems<AnimeResponse>,
    onErrorClick: () -> Unit,
    errorMessageMain: String? = ""
) {
    val context = LocalContext.current

    when (pagingItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            Timber.e("LoadState.NotLoading || errorMessageMain => $errorMessageMain")
            AnimeComponentGridPaging(
                pagingItems = pagingItems,
                context = context,
                errorMessage = errorMessageMain,
                onErrorClick = onErrorClick
            )
        }

        is LoadState.Error -> {
            Timber.e("LoadState.Error || errorMessageMain => $errorMessageMain")
            val error = pagingItems.loadState.refresh as LoadState.Error
            val errorMessage = when (error.error) {
                is HttpException -> "Sorry, Something went wrong!\nTap to retry"
                is IOException -> "Connection failed. Tap to retry!"
                else -> "Failed! Tap to retry!"
            }
            ErrorScreen(
                errorMessage = if (!errorMessageMain.isNullOrEmpty()) errorMessageMain else errorMessage,
                onErrorClick = onErrorClick
            )
        }

        is LoadState.Loading -> {
            Timber.e("LoadState.Loading || errorMessageMain => $errorMessageMain")
            LoadingScreen()
        }
    }
}

@Composable
fun AnimeGridList(
    homeUiState: HomeUiState,
    onErrorClick: () -> Unit,
) {
    val context = LocalContext.current

    if (homeUiState.isLoading) {
        Timber.e("HomeAnimeList | isLoading")
        LoadingScreen()
    } else {

        if (!homeUiState.isError) {
            Timber.e("HomeAnimeList | isNotLoading | Available - ${homeUiState.data.size}")
            if (homeUiState.data.isNotEmpty()) {
                AnimeComponentGrid(
                    items = homeUiState.data,
                    context = context,
                    onErrorClick = onErrorClick
                )
            } else {
                Timber.e("HomeAnimeList | isNotLoading | Empty - ${homeUiState.data.size}")
                ErrorScreen(errorMessage = "Empty", onErrorClick = onErrorClick)
            }

        } else {
            Timber.e("HomeAnimeList | isError | errorMessage => ${homeUiState.errorMessage}")
            ErrorScreen(errorMessage = homeUiState.errorMessage, onErrorClick = onErrorClick)
        }
    }
}

@Composable
fun AnimeComponentGrid(
    items: List<AnimeResponse>,
    context: Context,
    errorMessage: String? = "",
    onErrorClick: () -> Unit
) {
    if (items.isEmpty())
        ErrorScreen(errorMessage = errorMessage ?: "", onErrorClick = onErrorClick)
    else
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(end = 10.dp, start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(items) { item ->

                AnimeContent(
                    context = context,
                    imageUrl = item.images?.webp?.imageUrl ?: "",
                    title = item.title ?: "",
                    titleJapanese = item.titleJapanese ?: "",
                    favourite = item.favourite ?: false
                )
            }
        }

}

@Composable
fun AnimeComponentGridPaging(
    pagingItems: LazyPagingItems<AnimeResponse>,
    context: Context,
    errorMessage: String? = "",
    onErrorClick: () -> Unit
) {
    if (pagingItems.itemCount == 0)
        ErrorScreen(errorMessage = errorMessage ?: "", onErrorClick = onErrorClick)
    else
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(end = 10.dp, start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(pagingItems.itemCount) { itemCount ->

                AnimeContent(
                    context = context,
                    imageUrl = pagingItems[itemCount]?.images?.webp?.imageUrl ?: "",
                    title = pagingItems[itemCount]?.title ?: "",
                    titleJapanese = pagingItems[itemCount]?.titleJapanese ?: "",
                    favourite = pagingItems[itemCount]?.favourite ?: false
                )
            }
        }

}