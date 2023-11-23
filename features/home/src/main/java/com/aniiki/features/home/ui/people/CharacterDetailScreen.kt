package com.aniiki.features.home.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aniiki.features.home.ui.LoadingScreen
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.compose.fadingEdge
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.compose.theme.md_theme_dark_surface
import com.syakirarif.aniiki.compose.theme.md_theme_light_surface
import com.syakirarif.aniiki.core.utils.orNullEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun CharacterDetailMainScreen(
    peopleDetailViewModel: PeopleDetailViewModel,
    onVAClicked: (String) -> Unit
) {

    val charDetail by peopleDetailViewModel.charDetail.collectAsState()
//    val charId = charDetail.charId
//
    Timber.e("charDetail => ${charDetail.dataCharacterDetail.name}")
//
//    val bool = rememberSaveable { true }
//
//    LaunchedEffect(key1 = bool) {
//        peopleDetailViewModel.getCharacterDetail()
//    }

    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            if (isSystemInDarkTheme()) Color.Black else Color.White
                        )
                    )
                )
        ) {
            if (!charDetail.isLoading) {
                val dataChar = charDetail.dataCharacterDetail
                item {
                    16.spacer()
                    SectionPhoto(
                        imageUrl = dataChar.images.jpg.imageUrl,
                        title = dataChar.name.orNullEmpty(),
                        subtitle = dataChar.nameKanji.orNullEmpty(),
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                    16.spacer()
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                    4.spacer()
                    Text(
                        text = dataChar.about.orNullEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                    16.spacer()
                    if (dataChar.anime.isNotEmpty()) {
                        Text(
                            text = "Animeography",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                        4.spacer()
                        LazyRow {
                            items(dataChar.anime) { anime ->
                                CharsCard(
                                    imageUrl = anime.anime.images.jpg.imageUrl,
                                    title = anime.anime.title.orNullEmpty(),
                                    role = anime.role.orNullEmpty() + " Character",
                                    onCardClicked = { }
                                )
                            }
                        }

                    }
                    if (dataChar.voices.isNotEmpty()) {
                        Text(
                            text = "Voice Actors/Actresses",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                        4.spacer()
                        LazyRow {
                            items(dataChar.voices) { voice ->
                                CharsCard(
                                    imageUrl = voice.person.images.jpg.imageUrl,
                                    title = voice.person.name.orNullEmpty(),
                                    role = voice.language.orNullEmpty() + " VA",
                                    onCardClicked = { onVAClicked(voice.person.malId.toString()) }
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    LoadingScreen()
                }
            }

        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun PeopleDetailScreenPreview() {
//    PeopleDetailScreen()
//}

@Composable
fun SectionPhoto(
    imageUrl: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    val isInDarkTheme = isSystemInDarkTheme()

    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)

    Box(
        modifier
            .fillMaxSize()
//            .background(Color.DarkGray.copy(alpha = 0.5f))
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .height(400.dp)
        ) {
            GlideImage(
                imageModel = { imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                ),
                loading = { LoadingScreen() },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(4f, 4f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(4f, 4f),
                            blurRadius = 8f
                        )
                    ),
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
        ) {}
    }
}

@Composable
fun CharsCard(
    imageUrl: String,
    title: String,
    role: String,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier
            .width(205.dp)
            .height(265.dp)
            .padding(16.dp)
            .clickable {
                onCardClicked()
            }
    ) {
        GlideImage(
            imageModel = { imageUrl },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth(),
            loading = { LoadingScreen() },
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(color = Color.Black.copy(alpha = 0.4f))
                .padding(10.dp, 10.dp, 10.dp, 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    title,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
                Text(
                    role,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White.copy(alpha = 0.7F)
                )
            }
        }
    }
}