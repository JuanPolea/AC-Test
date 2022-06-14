package com.jfmr.ac.test.presentation.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jfmr.ac.test.presentation.ui.character.detail.view.CharacterDetailScreen
import com.jfmr.ac.test.presentation.ui.character.list.view.CharacterListScreen
import com.jfmr.ac.test.presentation.ui.main.AppState

@ExperimentalFoundationApi
@Composable
internal fun Navigation(appState: AppState) {
    NavHost(
        navController = appState.navController,
        startDestination = Feature.CHARACTERS.route
    ) {
        charactersNav(appState)
        locationsNav(appState)
    }
}

fun NavGraphBuilder.locationsNav(appState: AppState) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.LOCATIONS).route,
        route = Feature.LOCATIONS.route
    ) {
        composable(NavCommand.ContentType(Feature.LOCATIONS)) {
            Text(text = "jalskfd")
        }
        composable(NavCommand.ContentDetail(Feature.LOCATIONS)) {
            Text(text = "etail")
        }
    }
}

@ExperimentalFoundationApi
private fun NavGraphBuilder.charactersNav(
    appState: AppState,
) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.CHARACTERS).route,
        route = Feature.CHARACTERS.route
    ) {

        composable(NavCommand.ContentType(Feature.CHARACTERS)) {
            CharacterListScreen(modifier = Modifier, onClick = { resultsItem ->
                appState
                    .navController
                    .navigate(
                        NavCommand
                            .ContentDetail(Feature.CHARACTERS)
                            .createRoute(resultsItem.id)
                    )
            }
            )
        }
        composable(NavCommand.ContentDetail(Feature.CHARACTERS)) { backStackEntry ->
            CharacterDetailScreen(
                itemId = backStackEntry.findArgs(NavArg.ItemId),
                onUpClick = { appState.navController.popBackStack() }
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit,
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
