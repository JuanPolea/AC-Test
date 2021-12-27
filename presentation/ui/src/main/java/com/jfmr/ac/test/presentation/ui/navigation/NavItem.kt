package com.jfmr.ac.test.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class NavItem(
    val baseRoute: String,
    private val navArgs: List<NavArg> = emptyList()
) {

    val route = kotlin.run {
        val argKeys = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argKeys)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { it.navType }
    }

    object Main : NavItem("list")
    object Detail : NavItem(
        baseRoute = "detail",
        navArgs = listOf(NavArg.CharacterId)
    ) {
        fun createRoute(characterId: Int) = "$baseRoute/$characterId"
    }
}

enum class NavArg(
    val key: String,
    val navType: NavType<*>
) {
    CharacterId("characterId", NavType.IntType)
}