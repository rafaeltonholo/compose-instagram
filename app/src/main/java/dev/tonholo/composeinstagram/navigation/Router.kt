package dev.tonholo.composeinstagram.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Router {
    private val _currentRoute: MutableSharedFlow<Route> = MutableSharedFlow()
    val currentRoute = _currentRoute.asSharedFlow()

    suspend fun navigateTo(route: Route) {
        _currentRoute.emit(route)
    }
}
