package com.aniiki.features.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

interface HomeDestination {
    val icon: ImageVector
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

val homeTabRowScreens = listOf(Home, Schedule, Library)