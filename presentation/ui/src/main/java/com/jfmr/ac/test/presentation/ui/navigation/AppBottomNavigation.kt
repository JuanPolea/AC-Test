package com.jfmr.ac.test.presentation.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import timber.log.Timber

@Composable
fun AppBottomNavigation(currentRoute: String, onNavItemClick: (NavItem) -> Unit) {
    NavigationBar {
        NavItem.values().forEach {
            val title = stringResource(id = it.title)
            Timber.wtf(title)
            NavigationBarItem(
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
