package com.aniiki.features.home.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.aniiki.features.home.ui.detail.DetailViewModel
import com.aniiki.features.home.ui.home.HomeScreenApp
import com.aniiki.features.home.ui.home.HomeViewModel
import com.aniiki.features.home.ui.people.PeopleDetailViewModel
import com.aniiki.features.home.ui.schedule.ScheduleViewModel
import com.syakirarif.aniiki.compose.theme.BidayahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()
    private val peopleDetailViewModel: PeopleDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            BidayahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreenApp(
                        homeViewModel = homeViewModel,
                        scheduleViewModel = scheduleViewModel,
                        detailViewModel = detailViewModel,
                        peopleDetailViewModel = peopleDetailViewModel
                    )
                }
            }

        }
    }
}