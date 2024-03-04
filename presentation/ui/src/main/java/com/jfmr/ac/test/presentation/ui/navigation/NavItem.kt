package com.jfmr.ac.test.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

enum class NavItem(
    val navCommand: NavCommand
) {
    CHARACTERS(NavCommand.ContentType(Feature.CHARACTERS)),
    LOCATIONS(NavCommand.ContentType(Feature.LOCATIONS))
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentDetail(feature: Feature) : NavCommand(feature, "detail", listOf(NavArg.ItemId)) {
        fun createRoute(itemId: Int) = "${feature.route}/$subRoute/$itemId"
    }

    val route = kotlin.run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(feature.route, subRoute)
            .plus(argKeys)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { it.navType }
    }

}

enum class NavArg(
    val key: String,
    val navType: NavType<*>
) {
    ItemId("characterId", NavType.IntType),
}
