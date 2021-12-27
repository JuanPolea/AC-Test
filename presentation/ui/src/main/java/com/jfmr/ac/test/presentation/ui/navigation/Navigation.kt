package com.jfmr.ac.test.presentation.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jfmr.ac.test.presentation.ui.character.detail.view.Detail
import com.jfmr.ac.test.presentation.ui.character.list.view.CharacterListScreen
import com.jfmr.ac.test.presentation.ui.main.rememberAppState

@ExperimentalFoundationApi
@Composable
internal fun Navigation() {
    val appState = rememberAppState()
    NavHost(
        navController = appState.navController,
        startDestination = NavItem.Main.route
    ) {
        composable(NavItem.Main) {
            CharacterListScreen(modifier = Modifier) { resultsItem ->
                appState.navController.navigate(NavItem.Detail.createRoute(resultsItem.id))
            }
        }
        composable(NavItem.Detail) { backStackEntry ->
            Detail(
                itemId = backStackEntry.findArgs(NavArg.CharacterId),
                onUpClick = { appState.navController.popBackStack() }
            )
        }
    }
}


private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArgs(arg: NavArg): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}
