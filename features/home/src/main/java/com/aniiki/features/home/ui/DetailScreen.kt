@file:OptIn(ExperimentalMaterial3Api::class)

package com.aniiki.features.home.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aniiki.features.home.ui.state.DetailUiState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.response.anime.childs.Character
import com.syakirarif.aniiki.compose.fadingEdge
import com.syakirarif.aniiki.compose.pagerFadeTransition
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.compose.theme.md_theme_dark_surface
import com.syakirarif.aniiki.compose.theme.md_theme_light_surface
import com.syakirarif.aniiki.core.utils.orNullEmpty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@Composable
fun DetailMainScreen(
    detailViewModel: DetailViewModel,
    onBackPressed: () -> Unit,
    onPeopleClicked: (String) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DetailScreenTopBar(onBackPressed = onBackPressed)
            },
            bottomBar = {}
//        color = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            DetailMainScreen2(
                detailViewModel = detailViewModel,
                onBackPressed = onBackPressed,
                onPeopleClicked = onPeopleClicked,
                modifier = Modifier.padding(innerPadding)
//                modifier = Modifier.padding(
//                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
//                    bottom = innerPadding.calculateBottomPadding()
//                )
            )
        }
    }
}

@Composable
fun DetailScreenTopBar(onBackPressed: () -> Unit, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
//                        Text(
//                            anime.title.orNullEmpty(),
//                            style = MaterialTheme.typography.titleLarge,
//                            color = Color.White
//                        )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() })
            {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .shadow(8.dp),
                    tint = Color.White
                )
            }
        },
//                    colors = TopAppBarDefaults.topAppBarColors()
//                backgroundColor = MaterialTheme.colorScheme.background,
//                contentColor = MaterialTheme.colorScheme.background,
//                elevation = 0.dp
    )
}

@ExperimentalCoroutinesApi
@Composable
fun DetailMainScreen2(
    detailViewModel: DetailViewModel,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    onPeopleClicked: (String) -> Unit
) {

//    val systemUiController = rememberSystemUiController()
////    val useDarkIcons = !isSystemInDarkTheme()
////
//    DisposableEffect(systemUiController, useDarkIcons) {
//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent,
//            darkIcons = useDarkIcons
//        )
//
//        onDispose {}
//    }

    val systemUiController = rememberSystemUiController()

    DisposableEffect(key1 = systemUiController) {

//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent,
//            darkIcons = false
//        )

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose { }
    }


    val anime by detailViewModel.animeResponse.collectAsState()

//    detailViewModel.animeId = anime.malId.orNullEmpty()
//    detailViewModel.getAnimePictures4(anime.malId.orNullEmpty())
//    detailViewModel.getAnimePictures4("53887")
//    detailViewModel.getAnimePictures4()

//    val detailUiState by detailViewModel.detailUiState.collectAsState()

    val animePictures by detailViewModel.animePictures.collectAsState()

    val animeCharacters by detailViewModel.animeCharacters.collectAsState()


//    val animeId by detailViewModel.animeId.collectAsState()

//    detailViewModel.getAnimePictures(anime.malId.orNullEmpty())

    val posterSize = 600

    val context = LocalContext.current

    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)

    val isInDarkTheme = isSystemInDarkTheme()

    val textColor = if (isInDarkTheme) Color.White else Color.Black

    val bgColor = if (isInDarkTheme) md_theme_dark_surface else md_theme_light_surface

    val AnimeSaver = run {
        val animeId = ""

        mapSaver(
            save = { mapOf(animeId to it.malId) },
            restore = { AnimeResponse(malId = it[animeId] as Int) }
        )
    }

//    val animeSaveable = rememberSaveable {
//        mutableStateOf(animeResponse)
//    }

//    val anime = animeSaveable.value
//    val animeId = anime.malId.orNullEmpty()

//    LaunchedEffect(key1 = animeId) {
//        detailViewModel.getAnimePictures(animeId)
//        detailViewModel.getAnimeCharacters(animeId)
//    }

    val banner: MutableList<String> = mutableListOf()
    var bannerSet: Set<String> = mutableSetOf()

    if (!animePictures.isLoading) {
        if (animePictures.dataPictures.isNotEmpty()) {
            animePictures.dataPictures.forEach {
                banner.add(it.jpg.largeImageUrl)
            }
        }
    } else {
        banner.add(anime.images.jpg.largeImageUrl)
    }

    bannerSet = banner.toSet()
    banner.clear()
    bannerSet.forEach {
        banner.add(it)
    }

//    Timber.e("animePictures | data => ${animePictures.size}")

    Box(
//        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor)
    ) {
        Box(
            Modifier.align(Alignment.TopCenter)
        ) {
//            if (banner.isNotEmpty()) {
            DetailAnimePosterSlider(
                data = banner, modifier = Modifier
                    .height(posterSize.dp)
                    .align(
                        Alignment.TopCenter
                    )
            )
//            } else
//                GlideImage(
//                    imageModel = { anime.images.webp.largeImageUrl },
//                    imageOptions = ImageOptions(
//                        contentScale = ContentScale.Crop
//                    ),
//                    loading = { LoadingScreen() },
//                    modifier = Modifier
//                        //                .fillMaxSize()
//                        .height(posterSize.dp)
//                        //                .fillMaxHeight()
//                        .fillMaxWidth()
//                        .align(Alignment.TopCenter),
//                )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(60.dp)
                    .fillMaxWidth()
                    .fadingEdge(topFade)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                md_theme_dark_surface.copy(alpha = 0.5f),
                                md_theme_dark_surface
                            )
//                            colors = if (isInDarkTheme) listOf(
//                                md_theme_dark_surface.copy(alpha = 0.5f),
//                                md_theme_dark_surface
//                            ) else listOf(
//                                md_theme_light_surface.copy(alpha = 0.5f),
//                                md_theme_light_surface
//                            )
                        )
                    )
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxSize()
                .background(color = Color.Transparent)
        ) {
            item {
                (posterSize - 50).spacer()
                Column(
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(color = bgColor)
                ) {
                    20.spacer()
                    Box(
                        Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .width(40.dp)
                                .height(8.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray.copy(alpha = 0.5f))
                        ) {}
                    }
                    20.spacer()
                    Text(
                        text = anime.title.orNullEmpty(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = textColor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    )
                    10.spacer()
                    Text(
                        text = anime.synopsis.orNullEmpty(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    )
                    20.spacer()
                    Text(
                        text = "Genre",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    20.spacer()
                    GenreSection(anime = anime, modifier = Modifier.padding(start = 16.dp))
                    20.spacer()
                    Text(
                        text = "Information",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    20.spacer()
                    TableDetails(
                        anime = anime,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    20.spacer()
                    CharactersAndCastSection(
                        textColor = textColor,
                        animeCharacters = animeCharacters,
                        onPeopleClicked = onPeopleClicked
                    )
                    80.spacer()
                }
            }
        }
    }
}

@Composable
fun CharactersAndCastSection(
    textColor: Color,
    animeCharacters: DetailUiState,
    onPeopleClicked: (String) -> Unit
) {
    Text(
        text = "Characters & Casts",
        style = MaterialTheme.typography.headlineSmall,
        color = textColor,
        modifier = Modifier
            .padding(horizontal = 16.dp)
    )
    20.spacer()
    if (animeCharacters.isLoading) {
        LoadingScreen()
    } else {
        LazyRow {
            items(animeCharacters.dataCharacters) { item ->
                CharacterComponent(
                    item = item,
                    modifier = Modifier.clickable {
                        val charId = item.character.malId.toString()
                        onPeopleClicked(charId)
                    }
                )
            }
        }
    }
}

@Composable
fun GenreSection(anime: AnimeResponse, modifier: Modifier = Modifier) {

    val colors = listOf(Color(0xFFa3e0a3), Color(0xFF78b9f8), Color(0xFFfca595))

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(anime.genres) { i, item ->
            Box(
                Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = item.name.orNullEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors[i % colors.size],
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(colors[i % colors.size].copy(0.3f))
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxSize()
                )
            }
        }
    }

}

@Composable
fun TableDetails(anime: AnimeResponse?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        ItemDetail("Type", anime?.type.orNullEmpty())
        ItemDetail("Source", anime?.source.orNullEmpty())
        ItemDetail("Episodes", anime?.episodes.orNullEmpty())
        ItemDetail("Status", anime?.status.orNullEmpty())
        ItemDetail("Duration", anime?.duration.orNullEmpty())
        ItemDetail("Rating", anime?.rating.orNullEmpty())
        ItemDetail("Score", anime?.score.orNullEmpty())

    }
}

@Composable
fun ItemDetail(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = value,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailAnimePosterSlider(modifier: Modifier = Modifier, data: List<String>) {

    val isInDarkTheme = isSystemInDarkTheme()

    val pageState = rememberPagerState { data.size }
    val heightSize = WindowInsets.systemBars.asPaddingValues()
    var finishSwipe by remember { mutableStateOf(false) }

    val coroutineScope = CoroutineScope(Dispatchers.Main)

//    var visibility by remember { mutableStateOf(false) }

    val visibility = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    LaunchedEffect(key1 = finishSwipe) {
        launch {
            delay(3000)
            with(pageState) {
//                val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                var newPosition = pageState.currentPage + 1
                if (newPosition > data.lastIndex) newPosition = 0

//                visibility = false
                visibility.targetState = false

                animateScrollToPage(
                    page = newPosition,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )

                finishSwipe = !finishSwipe
//                visibility = true
                visibility.targetState = true
            }
        }
    }

    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)
    val topBottomFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.3f to Color.Red,
        0.7f to Color.Red,
        1f to Color.Transparent
    )
//    Box(modifier = Modifier.fillMaxSize()) {
    HorizontalPager(
        state = pageState,
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    when {
                        dragAmount < 0 -> {
                            coroutineScope.launch { /* right */
                                if (pageState.currentPage == data.lastIndex) {
                                    pageState.animateScrollToPage(0)
                                } else {
                                    pageState.animateScrollToPage(pageState.currentPage + 1)
                                }
                            }
                        }

                        dragAmount > 0 -> { /* left */
                            coroutineScope.launch {
                                if (pageState.currentPage == 0) {
                                    pageState.animateScrollToPage(data.lastIndex)
                                } else {
                                    pageState.animateScrollToPage(pageState.currentPage - 1)
                                }
                            }
                        }
                    }
                }
            }
    )
    { page ->
        GlideImage(
            imageModel = { data[page] },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
//                alignment = remember(pageState) {
//                    ParallaxAlignment(
//                        horizontalBias = {
//                            val adjustedOffset =
//                                pageState.currentPageOffsetFraction - pageState.initialPageOffsetFraction
//                            (adjustedOffset / pageState.pageCount.toFloat()).coerceIn(
//                                -1f,
//                                1f
//                            )
//                        }
//                    )
//                }
            ),
            loading = { LoadingScreen() },
            modifier = modifier
                .fillMaxWidth()
                .pagerFadeTransition(page = page, pagerState = pageState)
                .clickable {

                }
        )
    }
//        Box(
//            Modifier
//                .align(Alignment.BottomCenter)
//                .defaultMinSize(minHeight = 120.dp)
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .fadingEdge(topFade)
//                .background(
//                    brush = Brush.verticalGradient(
//                        colors = if (isInDarkTheme) listOf(
//                            md_theme_dark_surface.copy(alpha = 0.5f),
//                            md_theme_dark_surface
//                        ) else listOf(
//                            md_theme_light_surface.copy(alpha = 0.5f),
//                            md_theme_light_surface
//                        )
//                    )
//                )
//                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 8.dp)
//
//        )
//    }
}

@Composable
fun CharacterComponent(item: Character, modifier: Modifier = Modifier) {
//    val liked = remember { mutableStateOf(item.favourite) }
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .width(205.dp)
            .height(265.dp)
            .padding(16.dp)
    ) {
        GlideImage(
            imageModel = { item.character.images.jpg.imageUrl },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            loading = { LoadingScreen() },
        )
        Box(
            modifier = Modifier
//                .clip(CircleShape)
//                .background(Color.Black.copy(alpha = 0.5F))
//                .size(28.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(color = Color.Black.copy(alpha = 0.4f))
                .align(Alignment.TopStart)
                .clickable {
//                    liked.value = !liked.value
                })
        {
//            val tint by animateColorAsState(
//                if (liked.value) Color.Red
//                else Color.LightGray, label = ""
//            )
//            Icon(
//                if (liked.value) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
//                contentDescription = "",
//                tint = tint,
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .padding(6.dp)
//            )
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.character.name.orNullEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    item.role.orNullEmpty(),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White.copy(alpha = 0.7F)
                )
            }
        }

        if (item.voiceActors.isNotEmpty())
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(10.dp, 10.dp, 10.dp, 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                GlideImage(
                    imageModel = { item.voiceActors[0].person.images.jpg.imageUrl.orNullEmpty() },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                    ),

                    )
                16.spacer()
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        item.voiceActors[0].person.name.orNullEmpty(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                    Text(
                        item.voiceActors[0].language.orNullEmpty() + " VA",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.7F)
                    )
                }
            }
    }
}
