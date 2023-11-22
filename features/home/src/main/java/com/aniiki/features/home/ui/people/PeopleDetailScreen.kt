package com.aniiki.features.home.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.syakirarif.aniiki.apiservice.response.character.AnimeCharacterResponse
import com.syakirarif.aniiki.compose.fadingEdge
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.compose.theme.md_theme_dark_surface
import com.syakirarif.aniiki.compose.theme.md_theme_light_surface
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@Composable
fun PeopleDetailScreen(
    peopleDetailViewModel: PeopleDetailViewModel
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
                item {
                    16.spacer()
                    SectionPhoto(charDetail = charDetail.dataCharacterDetail)
                    16.spacer()
                    SectionDetail(
                        charDetail = charDetail.dataCharacterDetail,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
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
fun SectionPhoto(charDetail: AnimeCharacterResponse, modifier: Modifier = Modifier) {
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
                .padding(horizontal = 22.dp)
                .height(400.dp)
        ) {
            GlideImage(
                imageModel = { charDetail.images.jpg.imageUrl },
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
                    text = charDetail.name,
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
                    text = charDetail.nameKanji,
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
fun SectionDetail(charDetail: AnimeCharacterResponse, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge,
//            color = Color.Gray,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = charDetail.about,
            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}