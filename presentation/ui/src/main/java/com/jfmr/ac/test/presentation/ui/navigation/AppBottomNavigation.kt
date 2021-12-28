package com.jfmr.ac.test.presentation.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import timber.log.Timber

@Composable
fun AppBottomNavigation(currentRoute: String, onNavItemClick: (NavItem) -> Unit) {
    BottomNavigation {
        NavItem.values().forEach {
            val title = stringResource(id = it.title)
            Timber.wtf(title)
            BottomNavigationItem(
                selected = currentRoute.contains(it.navCommand.feature.route),
                onClick = {
                    onNavItemClick(it)
                },
                icon = {
                    Icon(imageVector = it.icon, contentDescription = title)
                },
                label = {
                    Text(text = title)
                }
            )
        }
    }
}
