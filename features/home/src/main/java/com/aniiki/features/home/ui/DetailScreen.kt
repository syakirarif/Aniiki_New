@file:OptIn(ExperimentalMaterial3Api::class)

package com.aniiki.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.fadingEdge
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.compose.theme.md_theme_dark_surface
import com.syakirarif.aniiki.compose.theme.md_theme_light_surface
import com.syakirarif.aniiki.core.utils.orNullEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber


@Composable
fun DetailMainScreen(detailViewModel: DetailViewModel, onBackPressed: () -> Unit) {
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
    modifier: Modifier = Modifier
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

    val animePictures by detailViewModel.animePictures2
        .collectAsState()

//    detailViewModel.getAnimePictures(anime.malId.orNullEmpty())

    val posterSize = 600

    val context = LocalContext.current

    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)

    val isInDarkTheme = isSystemInDarkTheme()

    val textColor = if (isInDarkTheme) Color.White else Color.Black

    val bgColor = if (isInDarkTheme) md_theme_dark_surface else md_theme_light_surface

    LaunchedEffect(key1 = animePictures) {
        Timber.e("animePictures | isLoading => ${animePictures.isLoading}")
        Timber.e("animePictures | isError => ${animePictures.isError}")

        if (!animePictures.isLoading) {
            Timber.e("animePictures | data => ${animePictures.dataPictures.size}")
        }
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
            GlideImage(
                imageModel = { anime.images.webp.largeImageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop
                ),
                loading = { LoadingScreen() },
                modifier = Modifier
                    //                .fillMaxSize()
                    .height(posterSize.dp)
                    //                .fillMaxHeight()
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(60.dp)
                    .fillMaxWidth()
                    .fadingEdge(topFade)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = if (isInDarkTheme) listOf(
                                md_theme_dark_surface.copy(alpha = 0.5f),
                                md_theme_dark_surface
                            ) else listOf(
                                md_theme_light_surface.copy(alpha = 0.5f),
                                md_theme_light_surface
                            )
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
                (posterSize - 100).spacer()
                Column(
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(color = bgColor)
                ) {
                    20.spacer()
                    Text(
                        text = anime.title.orNullEmpty(),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = textColor,
                        modifier = Modifier
                            .padding(start = 16.dp),
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
                        text = "Subject Details",
                        style = MaterialTheme.typography.headlineSmall,
                        color = textColor,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )
                    20.spacer()
                    TableDetails(
                        anime = anime,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    80.spacer()
                }

            }
        }

//        Column(
//        ) {
//            TopAppBar(
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Transparent.copy(
//                        alpha = 0.3f
//                    )
//                ),
//                title = {
//                    Text(
//                        anime.title.orNullEmpty(),
//                        style = MaterialTheme.typography.titleLarge,
//                        color = Color.White
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { onBackPressed() })
//                    {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = null,
//                            modifier = Modifier.padding(horizontal = 8.dp),
//                            tint = Color.White
//                        )
//                    }
//                },
////                    colors = TopAppBarDefaults.topAppBarColors()
////                backgroundColor = MaterialTheme.colorScheme.background,
////                contentColor = MaterialTheme.colorScheme.background,
////                elevation = 0.dp
//            )
//            Box(
//                contentAlignment = Alignment.BottomCenter,
//                modifier = Modifier.background(
//                    color = Color.Transparent
//                )
//            ) {
//
////                itemsIndexed(
////                    eduSubjectDetailsData
////                ) { i, item ->
////                    EduSubjectDetailListComponent(item, i)
////                }
//                }
//                Button(
//                    onClick = {
////                    navController.navigate(EduScreenRoutes.SubjectDetailDescriptionScreen.routes)
//                    },
//                    shape = 24.radius(),
//                    elevation = ButtonDefaults.buttonElevation(),
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
//                    enabled = true,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(80.dp)
//                        .background(Color.White)
//                        .padding(
//                            start = 32.dp,
//                            end = 32.dp,
//                            top = 16.dp,
//                            bottom = 16.dp
//                        )
//                ) {
//                    Text(
//                        modifier = Modifier.padding(
//                            start = 16.dp,
//                            top = 4.dp,
//                            bottom = 4.dp,
//                            end = 16.dp
//                        ),
//                        text = "Continue",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                }
//            }
//        }
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