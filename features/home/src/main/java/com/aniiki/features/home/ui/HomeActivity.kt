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
import com.aniiki.features.home.ui.people.PeopleDetailViewModel
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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.setDecorFitsSystemWindows(false); //also tried with true
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            )
//        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
//            MovieScreen(
//                viewModel = viewModel,
//                selectPoster = {
//                    debugPrint("MAL ID: $it")
//                },
//                lazyGridState = LazyGridState()
//            )


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
//                        modifier = Modifier
//                            .navigationBarsPadding()
                    )
                }
            }

        }
    }
}