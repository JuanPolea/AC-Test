package com.jfmr.ac.test.presentation.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jfmr.ac.test.presentation.ui.navigation.NavItem
import com.jfmr.ac.test.presentation.ui.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope


class AppState(
    val navController: NavHostController,
) {
    companion object {
        val BOTTOM_NAV_OPTIONS: List<NavItem> = listOf(NavItem.CHARACTERS, NavItem.LOCATIONS)
    }

    private val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()

    val showUpNavigation: Boolean
        @Composable get() = currentRoute !in NavItem.values().map { it.navCommand.route }

    val showBottomNavigation: Boolean
        @Composable get() = BOTTOM_NAV_OPTIONS.any { currentRoute.contains(it.navCommand.feature.route) }

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onNavItemClick(route: String) {
        navController.navigatePoppingUpToStartDestination(route)
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(navController, coroutineScope) {
    AppState(
        navController = navController
    )
}
