package com.aniiki.features.home.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.aniiki.features.home.ui.LoadingScreen
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.syakirarif.aniiki.compose.fadingEdge
import com.syakirarif.aniiki.compose.theme.md_theme_dark_surface
import com.syakirarif.aniiki.compose.theme.md_theme_light_surface

@Composable
fun PeopleDetailScreen() {
    Scaffold(
        topBar = {},
        bottomBar = {}
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(paddingValues)
        ) {
            item {
                SectionPhoto()
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
fun SectionPhoto(modifier: Modifier = Modifier) {
    val isInDarkTheme = isSystemInDarkTheme()

    val topFade = Brush.verticalGradient(0f to Color.Transparent, 0.3f to Color.Red)

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = 0.5f))
    ) {
        GlideImage(
            imageModel = { "https://cdn.myanimelist.net/images/characters/4/457933.jpg" },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
            ),
            loading = { LoadingScreen() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 22.dp)
                .fillMaxWidth()
                .height(400.dp)
                .clip(RoundedCornerShape(16.dp))
        )
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