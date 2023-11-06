package com.aniiki.features.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.syakirarif.aniiki.compose.spacer
import com.syakirarif.aniiki.core.utils.getCurrentDay

@Composable
fun ScheduleMainScreen(scheduleViewModel: ScheduleViewModel) {

    val today = getCurrentDay().replaceFirstChar { it.uppercase() }

    val animeScheduleState by scheduleViewModel.animeScheduleState.collectAsState()
    val animeSchedulePagingItems = animeScheduleState.dataPaging.collectAsLazyPagingItems()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "Today's Schedule Anime ($today)",
                style = MaterialTheme.typography.titleLarge
            )
            AnimeGridList(
                pagingItems = animeSchedulePagingItems,
                onErrorClick = { scheduleViewModel.fetchAnimeSchedulePaging() },
                errorMessageMain = animeScheduleState.errorMessage
            )
            8.spacer()
        }
    }
}