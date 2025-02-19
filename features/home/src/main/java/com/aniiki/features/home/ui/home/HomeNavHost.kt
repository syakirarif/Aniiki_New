package com.aniiki.features.home.ui.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aniiki.features.home.ui.detail.DetailViewModel
import com.aniiki.features.home.ui.library.LibraryMainScreen
import com.aniiki.features.home.ui.schedule.ScheduleMainScreen
import com.aniiki.features.home.ui.schedule.ScheduleViewModel
import com.aniiki.features.home.ui.search.SearchMainScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.syakirarif.aniiki.core.utils.orNullEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    navHostController2: NavHostController,
    homeViewModel: HomeViewModel,
    scheduleViewModel: ScheduleViewModel,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(key1 = systemUiController, key2 = useDarkIcons) {

        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
        )

        onDispose { }
    }

    NavHost(
        navController = navHostController2,
        startDestination = Home.route,
        modifier = modifier
    ) {
        composable(route = Home.route) {
            HomeMainScreen(
                homeViewModel = homeViewModel,
                onItemClicked = { item ->
                    detailViewModel.setAnimeResponse(item)
                    detailViewModel.getAnimePictures(item?.malId.orNullEmpty())
                    detailViewModel.getAnimeCharacters(item?.malId.orNullEmpty())
                    navHostController.navigate(DetailAnime.route)
                }
            )
        }
        composable(route = Schedule.route) {
            ScheduleMainScreen(
                scheduleViewModel = scheduleViewModel,
                onItemClicked = { item ->
                    detailViewModel.setAnimeResponse(item)
                    detailViewModel.getAnimePictures(item?.malId.orNullEmpty())
                    detailViewModel.getAnimeCharacters(item?.malId.orNullEmpty())
                    navHostController.navigate(DetailAnime.route)
                }
            )
        }
        composable(route = Library.route) {
            LibraryMainScreen(screenName = Library.route)
        }
        composable(route = Search.route) {
            SearchMainScreen(screenName = Search.route)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }