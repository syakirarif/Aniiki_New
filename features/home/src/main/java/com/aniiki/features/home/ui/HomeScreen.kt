@file:OptIn(ExperimentalMaterial3Api::class)

package com.aniiki.features.home.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.spacer
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@Composable
fun HomeScreenApp(homeViewModel: HomeViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    homeViewModel.fetchAnimePaging()

    val pagingItems = homeViewModel.animePaging.value.collectAsLazyPagingItems()
    val pagingItemsTopAiring = homeViewModel.animeTopAiringPaging.value.collectAsLazyPagingItems()
    val pagingItemsTopUpcoming =
        homeViewModel.animeTopUpcomingPaging.value.collectAsLazyPagingItems()
    val pagingItemsTopMostPopular =
        homeViewModel.animeTopMostPopularPaging.value.collectAsLazyPagingItems()
    val errorMessage by homeViewModel.errorMessage

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { HomeTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    HomeAnimeHeading(title = "Summer 2023 Anime")
                    HomeAnimeList(
                        pagingItems = pagingItems,
                        onErrorClick = { homeViewModel.fetchAnimePaging() },
                        errorMessageMain = errorMessage
                    )
                    8.spacer()
                    HomeAnimeHeading(title = "Top Airing Anime")
                    HomeAnimeList(
                        pagingItems = pagingItemsTopAiring,
                        onErrorClick = { homeViewModel.fetchAnimeTopAiringPaging() })
                    8.spacer()
                    HomeAnimeHeading(title = "Top Upcoming Anime")
                    HomeAnimeList(
                        pagingItems = pagingItemsTopUpcoming,
                        onErrorClick = { homeViewModel.fetchAnimeTopUpcomingPaging() })
                    8.spacer()
                    HomeAnimeHeading(title = "Most Popular Anime All the Time")
                    HomeAnimeList(
                        pagingItems = pagingItemsTopMostPopular,
                        onErrorClick = { homeViewModel.fetchAnimeTopMostPopularPaging() })
                    8.spacer()
                }
            }
        }
    }
}

@Composable
fun HomeTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Aniiki",
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}

@Composable
fun HomeAnimeHeading(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title, style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun HomeAnimeList(
    pagingItems: LazyPagingItems<AnimeResponse>,
    onErrorClick: () -> Unit,
    errorMessageMain: String? = ""
) {
    val context = LocalContext.current

    when (pagingItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            Timber.e("LoadState.NotLoading || errorMessageMain => $errorMessageMain")
            AnimeComponent(
                pagingItems = pagingItems,
                context = context,
                errorMessage = errorMessageMain
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
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(161.25.dp) // maintain the vertical space between two categories
                    .clickable {
                        onErrorClick()
                    }
            ) {
                Text(
                    text = if (!errorMessageMain.isNullOrEmpty()) errorMessageMain else errorMessage,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFE28B8B),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        is LoadState.Loading -> {
            Timber.e("LoadState.Loading || errorMessageMain => $errorMessageMain")
            LoadingScreen()
        }
    }
}

@Composable
fun ErrorScreen(errorMessage: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(161.25.dp) // maintain the vertical space between two categories
            .clickable {
//                onErrorClick()
            }
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            color = Color(0xFFE28B8B),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AnimeComponent(
    pagingItems: LazyPagingItems<AnimeResponse>,
    context: Context,
    errorMessage: String? = ""
) {
    if (pagingItems.itemCount == 0)
        ErrorScreen(errorMessage = errorMessage ?: "")
    else
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp)
        ) {

            items(pagingItems.itemCount) { itemCount ->
                val liked = remember { mutableStateOf(pagingItems[itemCount]?.favourite) }
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .width(205.dp)
                        .height(255.dp)
                        .padding(start = 16.dp)
                ) {
                    GlideImage(
                        imageModel = { pagingItems[itemCount]?.images?.webp?.imageUrl ?: "" },
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop
                        ),
                        loading = { LoadingScreen() },
                        modifier = Modifier
                            .fillMaxWidth()
//                        .padding(4.dp)
//                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                Toast
                                    .makeText(
                                        context,
                                        pagingItems[itemCount]?.title ?: "",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                    )
                    Row(
                        modifier = Modifier
//                        .padding(16.dp, 16.dp, 0.dp, 16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                            .background(color = Color.Black.copy(alpha = 0.5f)),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .padding(top = 8.dp, start = 8.dp, end = 0.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = pagingItems[itemCount]?.title ?: "",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = pagingItems[itemCount]?.titleJapanese ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(
                            modifier = Modifier.weight(1f),
                            onClick = { liked.value = !liked.value!! })
                        {
                            val tint by animateColorAsState(
                                if (liked.value!!) Color.Red
                                else Color.LightGray, label = ""
                            )
                            Icon(
                                if (liked.value!!) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "",
                                tint = tint
                            )
                        }
                    }
                }
            }
        }

}

@Composable
fun AnimeListGrid(pagingItems: LazyPagingItems<AnimeResponse>, context: Context) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
//                columns = GridCells.Adaptive(minSize = 138.dp),
//                    state = lazyGridState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = { anime -> pagingItems[anime]?.malId ?: 0 }) { anime ->
            GlideImage(
                imageModel = { pagingItems[anime]?.images?.webp?.imageUrl ?: "" },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop
                ),
                loading = { LoadingScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
//                            .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                pagingItems[anime]?.title ?: "",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
            )
        }
    }
}