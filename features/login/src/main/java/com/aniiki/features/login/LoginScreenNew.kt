package com.aniiki.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.apiservice.utils.NetworkState
import com.syakirarif.aniiki.apiservice.utils.onLoading

@Composable
fun AnimeScreen() {
    val viewModel: LoginVMNew = hiltViewModel()
    val lazyColumnListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val canPage by viewModel.canPaginate
    val shouldStartPaginate = remember {
        derivedStateOf {
            canPage && (lazyColumnListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: -9) >= (lazyColumnListState.layoutInfo.totalItemsCount - 6)
        }
    }

    val networkState: NetworkState by viewModel.loadingState
    val animes by viewModel.anime

//    LaunchedEffect(key1 = shouldStartPaginate.value) {
//        if (shouldStartPaginate.value && networkState == NetworkState.IDLE)
//            viewModel.fetchAnimeSeason()
//    }

    val animeList: List<AnimeResponse>? by viewModel.animeList.collectAsState(initial = listOf())

    if (!animeList.isNullOrEmpty()) {
        LazyColumn(state = lazyColumnListState) {
            items(animeList!!) { anime ->
                PlantCard(
                    name = anime.title,
                    description = anime.synopsis,
                    imageUrl = anime.images?.webp?.imageUrl
                )
            }
        }
    }


//    LazyVerticalGrid(
//        state = lazyGridState,
//        columns = GridCells.Fixed(2),
//        modifier = Modifier
//            .statusBarsPadding()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        paging(
//            items = animes,
//            currentIndexFlow = viewModel.animePageStateFlow,
//            fetch = { viewModel.fetchNextPage() }
//        ) {anime ->
//            PlantCard(
//                name = anime.title,
//                description = anime.synopsis,
//                imageUrl = anime.images?.webp?.imageUrl
//            )
//        }
//    }

    networkState.onLoading {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun PlantCard(name: String?, description: String?, imageUrl: String?) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Fit,
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
fun AnimePoster(
    anime: AnimeResponse,
    selectPoster: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(290.dp)
            .clickable(
                onClick = {
                    selectPoster()
                }
            ),
        color = MaterialTheme.colorScheme.onBackground
    ) {

        Text(
            text = anime.title ?: "",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.85f)
                .padding(horizontal = 8.dp)
        )
    }
}