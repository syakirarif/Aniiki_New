@file:OptIn(ExperimentalMaterial3Api::class)

package com.aniiki.features.home.ui

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.radius
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.core.utils.orNullEmpty

@Composable
fun DetailMainScreen(detailViewModel: DetailViewModel, onBackPressed: () -> Unit) {

    val anime by detailViewModel.animeResponse.collectAsState()

    val posterSize = 600

    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.background(color = Color.Black)
    ) {
        Box(
            Modifier.align(Alignment.TopCenter)
        ) {
            GlideImage(
                imageModel = { anime.images?.webp?.largeImageUrl },
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
                    .height(70.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black
                            )
                        )
                    )
                    .align(Alignment.BottomCenter),
            )
        }

        Column(
        ) {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent.copy(
                        alpha = 0.3f
                    )
                ),
                title = {
                    Text(
                        "${anime.title}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() })
                    {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            tint = Color.White
                        )
                    }
                },
//                    colors = TopAppBarDefaults.topAppBarColors()
//                backgroundColor = MaterialTheme.colorScheme.background,
//                contentColor = MaterialTheme.colorScheme.background,
//                elevation = 0.dp
            )
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.background(
                    color = Color.Transparent
                )
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 75.dp)
//                        .padding(paddingValues)
                ) {
                    item {
                        500.spacer()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                                .background(Color.White)
                        ) {
                            20.spacer()
                            Text(
                                text = "${anime.title}",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 16.dp),
                            )
                            10.spacer()
                            Text(
                                "${anime.synopsis}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                            )
                            20.spacer()
                            Text(
                                text = "Subject Details",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                            )
                            20.spacer()
                            TableDetails(
                                anime = anime,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            120.spacer()
                        }

                    }
//                itemsIndexed(
//                    eduSubjectDetailsData
//                ) { i, item ->
//                    EduSubjectDetailListComponent(item, i)
//                }
                }
                Button(
                    onClick = {
//                    navController.navigate(EduScreenRoutes.SubjectDetailDescriptionScreen.routes)
                    },
                    shape = 24.radius(),
                    elevation = ButtonDefaults.buttonElevation(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.White)
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 4.dp,
                            bottom = 4.dp,
                            end = 16.dp
                        ),
                        text = "Continue",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
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
        Text(text = title, modifier = Modifier.weight(1f))
        Text(text = value, modifier = Modifier.weight(1f))
    }
}