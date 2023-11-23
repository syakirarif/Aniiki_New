package com.aniiki.features.home.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aniiki.features.home.ui.LoadingScreen
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.core.utils.orNullEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun PeopleDetailMainScreen(
    peopleDetailViewModel: PeopleDetailViewModel
) {

    val peopleDetail by peopleDetailViewModel.peopleDetail.collectAsState()

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
            if (!peopleDetail.isLoading) {
                val dataPeople = peopleDetail.dataPeopleDetail
                item {
                    16.spacer()
                    SectionPhoto(
                        imageUrl = dataPeople.images.jpg.imageUrl,
                        title = dataPeople.name,
                        subtitle = dataPeople.familyName + " " + dataPeople.givenName,
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
                        text = dataPeople.about.orNullEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                    16.spacer()
                    if (dataPeople.anime.isNotEmpty()) {
                        Text(
                            text = "Animeography",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                        4.spacer()
                        LazyRow {
                            items(dataPeople.anime) { anime ->
                                CharsCard(
                                    imageUrl = anime.anime.images.jpg.imageUrl,
                                    title = anime.anime.title,
                                    role = anime.role,
                                    onCardClicked = { }
                                )
                            }
                        }

                    }
                    if (dataPeople.voices.isNotEmpty()) {
                        Text(
                            text = "Voiced Characters",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                        4.spacer()
                        LazyRow {
                            items(dataPeople.voices) { voice ->
                                CharsCard(
                                    imageUrl = voice.character.images.jpg.imageUrl,
                                    title = voice.character.name.orNullEmpty(),
                                    role = voice.role.orNullEmpty(),
                                    onCardClicked = { }
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