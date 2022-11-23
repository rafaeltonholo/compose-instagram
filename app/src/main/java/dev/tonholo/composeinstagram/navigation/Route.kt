package dev.tonholo.composeinstagram.navigation

sealed interface Route {
    object Home : Route
    object Profile : Route
}
