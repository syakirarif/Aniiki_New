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

@Composable
fun PeopleDetailScreen() {
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
                            Color.White
                        )
                    )
                )
        ) {
            item {
                16.spacer()
                SectionPhoto()
                16.spacer()
                SectionDetail(modifier = Modifier.padding(horizontal = 22.dp))
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
                imageModel = { "https://cdn.myanimelist.net/images/characters/4/457933.jpg" },
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
                    text = "Anya Forger",
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
                    text = "アーニャ・フォージャー",
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
fun SectionDetail(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Anya is a young girl who can read other people's thoughts and is the only one who knows of her family's secrets. She is 4 or 5 years old, but claims to be 6. Originally an experimental human test subject dubbed \\\"Subject 007,\\\" she escaped and moved from one orphanage to another before being adopted by Loid. She likes spy anime and thinks anything involving \\\"secrets\\\" and \\\"missions\\\" are exciting.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}