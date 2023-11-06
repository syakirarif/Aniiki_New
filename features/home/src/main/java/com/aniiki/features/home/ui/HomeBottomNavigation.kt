package com.aniiki.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun HomeBottomNavigation(
    allScreens: List<HomeDestination>,
    onTabSelected: (HomeDestination) -> Unit,
    currentScreen: HomeDestination
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        allScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen == screen,
                onClick = { onTabSelected(screen) },
                icon = {
                    HomeBottomNavigationIcon(
                        image = screen.icon,
                        name = screen.route,
                        isSelected = currentScreen == screen
                    )
                },
                label = {
                    Text(screen.route, style = MaterialTheme.typography.labelMedium)
                }
            )
        }
    }
}

@Composable
fun HomeBottomNavigationIcon(
    image: ImageVector,
    name: String,
    isSelected: Boolean
) {
    Image(
        imageVector = image,
        colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .height(24.dp)
            .width(24.dp),
        contentDescription = name
    )
}