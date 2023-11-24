package com.aniiki.features.home.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

interface HomeDestination {
    val icon: ImageVector
    val route: String
}

interface NavScreen {
    val route: String
}

object Home : HomeDestination {
    override val icon = Icons.Filled.Home
    override val route = "Home"
}

object Schedule : HomeDestination {
    override val icon = Icons.Filled.DateRange
    override val route = "Schedule"
}

object Library : HomeDestination {
    override val icon = Icons.Filled.Menu
    override val route = "Library"
}

object Search : HomeDestination {
    override val icon = Icons.Filled.Search
    override val route = "Search"
}

object Dashboard : NavScreen {
    override val route = "dashboard"
}

object DetailAnime : NavScreen {
    override val route = "detail"
}

object DetailCharacter : NavScreen {
    override val route = "detailCharacter"
}

object DetailPeople : NavScreen {
    override val route = "detailPeople"
}

val homeTabRowScreens = listOf(Home, Schedule, Search, Library)