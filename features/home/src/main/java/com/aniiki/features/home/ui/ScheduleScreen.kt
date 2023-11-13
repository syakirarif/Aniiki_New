package com.aniiki.features.home.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.syakirarif.aniiki.apiservice.response.anime.AnimeResponse
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.core.utils.getCurrentDay

@Composable
fun ScheduleMainScreen(
    scheduleViewModel: ScheduleViewModel,
    onItemClicked: (AnimeResponse?) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
//    val color = MaterialTheme.colorScheme.surface
//    val color = NavigationBarDefaults.containerColor
//    val color = MaterialTheme.colorScheme.contentColorFor(containerColor)

    DisposableEffect(key1 = systemUiController) {

//        systemUiController.setSystemBarsColor(
//            color = Color.Transparent,
//            darkIcons = useDarkIcons
//        )

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )

        onDispose { }
    }

    val today = getCurrentDay().replaceFirstChar { it.uppercase() }

    val animeSchedulePagingState by scheduleViewModel.animeSchedulePagingState.collectAsState()
    val animeSchedulePagingItems = animeSchedulePagingState.dataPaging.collectAsLazyPagingItems()

    val animeScheduleState by scheduleViewModel.animeScheduleState.collectAsState()

    val heightSize = WindowInsets.systemBars.asPaddingValues()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    top = heightSize.calculateTopPadding(),
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Today's Schedule Anime ($today)",
                style = MaterialTheme.typography.titleLarge
            )
//            AnimeGridListPaging(
//                pagingItems = animeSchedulePagingItems,
//                onErrorClick = { scheduleViewModel.fetchAnimeSchedulePaging() },
//                errorMessageMain = animeScheduleState.errorMessage
//            )
            AnimeGridList(
                homeUiState = animeScheduleState,
                onErrorClick = {},
                onItemClicked = onItemClicked
            )
            8.spacer()
        }
    }
}