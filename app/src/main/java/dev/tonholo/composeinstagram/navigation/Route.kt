package dev.tonholo.composeinstagram.navigation

import dev.tonholo.composeinstagram.domain.User

sealed interface Route {
    object Home : Route

    data class Profile(val user: User) : Route
}
