package com.aniiki.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    navHostController2: NavHostController,
    homeViewModel: HomeViewModel,
    scheduleViewModel: ScheduleViewModel,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier
) {
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
                    navHostController.navigate(DetailAnime.route)
                }
            )
        }
        composable(route = Schedule.route) {
            ScheduleMainScreen(
                scheduleViewModel = scheduleViewModel,
                onItemClicked = { item ->
                    detailViewModel.setAnimeResponse(item)
                    navHostController.navigate(DetailAnime.route)
                }
            )
        }
        composable(route = Library.route) {
            LibraryMainScreen(screenName = Library.route)
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