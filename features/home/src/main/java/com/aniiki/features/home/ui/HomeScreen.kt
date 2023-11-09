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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.core.utils.getCurrentAnimeSeason
import com.syakirarif.aniiki.core.utils.getCurrentYear
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@Composable
fun HomeScreenApp(
    homeViewModel: HomeViewModel,
    scheduleViewModel: ScheduleViewModel,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        homeTabRowScreens.find { it.route == currentDestination?.route } ?: Dashboard

    val navController2 = rememberNavController()
    val currentBackStack2 by navController2.currentBackStackEntryAsState()
    val currentDestination2 = currentBackStack2?.destination
    val currentScreen2 =
        homeTabRowScreens.find { it.route == currentDestination2?.route } ?: Home

    NavHost(
        navController = navController,
        startDestination = Dashboard.route,
        modifier = modifier
    ) {
        composable(route = Dashboard.route) {
            Scaffold(
                modifier = modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { HomeTopAppBar(scrollBehavior = scrollBehavior) },
                bottomBar = {
                    HomeBottomNavigation(
                        allScreens = homeTabRowScreens,
                        onTabSelected = { newScreen ->
                            navController2.navigateSingleTopTo(newScreen.route)
                        },
                        currentScreen = currentScreen2
                    )
                }
            ) { innerPadding ->
                HomeNavHost(
                    navHostController = navController,
                    navHostController2 = navController2,
                    homeViewModel = homeViewModel,
                    scheduleViewModel = scheduleViewModel,
                    detailViewModel = detailViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
        composable(route = DetailAnime.route) {
            DetailMainScreen(
                detailViewModel = detailViewModel,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }


}

@Composable
fun HomeMainScreen(homeViewModel: HomeViewModel, onItemClicked: (AnimeResponse?) -> Unit) {

    val animeTopAiring by homeViewModel.animeTopAiring.collectAsState()
    val animeTopUpcomingState by homeViewModel.animeTopUpcomingState.collectAsState()
    val animeSeasonState by homeViewModel.animeSeasonState.collectAsState()
    val animeSeasonPagingItems = animeSeasonState.dataPaging.collectAsLazyPagingItems()

    val season =
        "${getCurrentAnimeSeason().replaceFirstChar { it.uppercase() }} ${getCurrentYear()}"

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                HomeAnimeHeading(title = "$season Anime")
                HomeAnimeList(
                    pagingItems = animeSeasonPagingItems,
                    onErrorClick = { homeViewModel.fetchAnimePaging() },
                    errorMessageMain = animeSeasonState.errorMessage,
                    onItemClicked = onItemClicked
                )
                8.spacer()
                HomeAnimeHeading(title = "Top 10 Airing Anime")
                HomeAnimeList(
                    homeUiState = animeTopAiring,
                    onErrorClick = { },
                    onItemClicked = onItemClicked
                )
                8.spacer()
                HomeAnimeHeading(title = "Top 10 Upcoming Anime")
                HomeAnimeList(
                    homeUiState = animeTopUpcomingState,
                    onErrorClick = { },
                    onItemClicked = onItemClicked
                )
                8.spacer()
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
    homeUiState: HomeUiState,
    onErrorClick: () -> Unit,
    onItemClicked: (AnimeResponse?) -> Unit
) {
    val context = LocalContext.current

    if (homeUiState.isLoading) {
        Timber.e("HomeAnimeList | isLoading")
        LoadingScreen()
    } else {

        if (!homeUiState.isError) {
            Timber.e("HomeAnimeList | isNotLoading | Available - ${homeUiState.data.size}")
            if (homeUiState.data.isNotEmpty()) {
                AnimeComponent(
                    items = homeUiState.data,
                    context = context,
                    onErrorClick = onErrorClick,
                    onItemClicked = onItemClicked
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
fun HomeAnimeList(
    pagingItems: LazyPagingItems<AnimeResponse>,
    onErrorClick: () -> Unit,
    errorMessageMain: String? = "",
    onItemClicked: (AnimeResponse?) -> Unit
) {
    val context = LocalContext.current

    when (pagingItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            Timber.e("LoadState.NotLoading || errorMessageMain => $errorMessageMain")
            AnimeComponent(
                pagingItems = pagingItems,
                context = context,
                errorMessage = errorMessageMain,
                onErrorClick = onErrorClick,
                onItemClicked = onItemClicked
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
fun ErrorScreen(errorMessage: String, onErrorClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(161.25.dp) // maintain the vertical space between two categories
            .clickable {
                onErrorClick()
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
    errorMessage: String? = "",
    onErrorClick: () -> Unit,
    onItemClicked: (AnimeResponse?) -> Unit
) {
    if (pagingItems.itemCount == 0)
        ErrorScreen(errorMessage = errorMessage ?: "", onErrorClick = onErrorClick)
    else
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp)
        ) {

            items(pagingItems.itemCount) { itemCount ->

                AnimeContent(
                    context = context,
                    item = pagingItems[itemCount],
                    onItemClicked = onItemClicked,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

}

@Composable
fun AnimeComponent(
    items: List<AnimeResponse>,
    context: Context,
    errorMessage: String? = "",
    onErrorClick: () -> Unit,
    onItemClicked: (AnimeResponse?) -> Unit
) {
    if (items.isEmpty())
        ErrorScreen(errorMessage = errorMessage ?: "", onErrorClick = onErrorClick)
    else
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp)
        ) {

            items(items) { item ->

                AnimeContent(
                    context = context,
                    item = item,
                    onItemClicked = onItemClicked,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

}

@Composable
fun AnimeContent(
    context: Context,
    item: AnimeResponse?,
    onItemClicked: (AnimeResponse?) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageUrl: String = item?.images?.webp?.imageUrl ?: ""
    val title: String = item?.title ?: ""
    val titleJapanese: String = item?.titleJapanese ?: ""
    val favourite: Boolean = item?.favourite ?: false
    val liked = rememberSaveable { mutableStateOf(favourite) }

    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .width(205.dp)
            .height(255.dp)
    ) {
        GlideImage(
            imageModel = { imageUrl },
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
                    onItemClicked(item)
//                    Toast
//                        .makeText(
//                            context,
//                            title,
//                            Toast.LENGTH_SHORT
//                        )
//                        .show()
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
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = titleJapanese,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = { liked.value = !liked.value })
            {
                val tint by animateColorAsState(
                    if (liked.value) Color.Red
                    else Color.LightGray, label = ""
                )
                Icon(
                    if (liked.value) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "",
                    tint = tint
                )
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