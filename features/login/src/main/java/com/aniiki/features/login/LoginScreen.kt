package com.aniiki.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.custom.NetworkImage
import com.syakirarif.aniiki.compose.custom.StaggeredVerticalGrid

//@Composable
//fun LoginScreen(
//    result: List<AnimeResponse> = listOf()
//) {
////    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
////    val result by viewModel.getAnimeSeason2().observeAsState()
////    val result: List<AnimeResponse> = listOf()
//
//    LaunchedEffect(key1 = Unit) {
////        viewModel.getAnimeList()
//
//        Timber.e("LAUNCHED EFFECT")
//    }
//
//    Timber.e("result => $result")
//
//
//    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) },
//        modifier = Modifier.fillMaxSize()
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//        ) {
////            LoginContent(loading = uiState.isLoading, data = uiState.data)
////            when (result.status) {
////                Status.SUCCESS -> {
////                    result.data?.let {
////                        it.forEach { each ->
////                            ShowDataScreen(resp = each)
////                        }
////                    }
////                }
////
////                Status.LOADING -> {
////                    LoadingScreen()
////                }
////
////                else -> {
////                    NoDataScreen(message = "Error")
////                }
////            }
//        }
//    }
//
//}

//@Composable
//fun LoginContent(
//    loading: Boolean,
//    data: List<AnimeResponse>
//) {
//    val lazyColumnListState = rememberLazyListState()
//    if (loading) {
//        LoadingScreen()
//    } else {
//        AnimePosters(posters = data, selectPoster = {
//            Timber.e("MAL ID => $it")
//        })
////        LazyColumn(state = lazyColumnListState) {
////            items(data) { anime ->
////                AnimeCard(resp = anime)
////            }
////        }
//    }
//}

//@Composable
//fun ShowDataScreen(resp: AnimeResponse) {
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = "${resp.title}", style = MaterialTheme.typography.titleMedium)
//        Text(
//            text = "${resp.titleJapanese}",
//            style = MaterialTheme.typography.titleSmall
//        )
//        Divider(color = Color.Blue, thickness = 1.dp)
//    }
//}

//@Composable
//fun AnimeCard(resp: AnimeResponse) {
//    Card(
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth()
//            .wrapContentHeight(),
//        shape = MaterialTheme.shapes.medium,
//        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
////        backgroundColor = MaterialTheme.colors.surface,
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Image(
//                painter = rememberAsyncImagePainter(resp.images?.webp?.imageUrl),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(130.dp)
//                    .padding(8.dp),
//                contentScale = ContentScale.Fit,
//            )
//            Column(Modifier.padding(8.dp)) {
//                Text(
//                    text = resp.title ?: "",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.onSurface,
//                )
//                Text(
//                    text = resp.synopsis ?: "",
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//            }
//        }
//    }
//}

@Composable
fun NoDataScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message, color = Color.Gray)
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.Blue)
    }
}

@Composable
fun MainAnimeLayout(
    posters: List<AnimeResponse>,
    selectPoster: (Int?) -> Unit,
) {
    Scaffold { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        AnimePosters(modifier = modifier, posters = posters, selectPoster = selectPoster)
    }
}

@Composable
fun AnimePosters(
    modifier: Modifier = Modifier,
    posters: List<AnimeResponse>,
    selectPoster: (Int?) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            posters.forEach { poster ->
                key(poster.malId) {
                    AnimePoster(
                        poster = poster,
                        selectPoster = selectPoster
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimePoster(
    modifier: Modifier = Modifier,
    poster: AnimeResponse,
    selectPoster: (Int?) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                onClick = { selectPoster(poster.malId) }
            ),
        color = MaterialTheme.colorScheme.onBackground,
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        ConstraintLayout {
            val (image, title, content) = createRefs()
            NetworkImage(
                modifier = Modifier
                    .aspectRatio(0.8f)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    },
                url = poster.images?.webp?.imageUrl ?: "",
            )

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        centerHorizontallyTo(parent)
                        top.linkTo(image.bottom)
                    }
                    .padding(8.dp),
                text = poster.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        centerHorizontallyTo(parent)
                        top.linkTo(title.bottom)
                    }
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
                text = poster.titleJapanese ?: "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}