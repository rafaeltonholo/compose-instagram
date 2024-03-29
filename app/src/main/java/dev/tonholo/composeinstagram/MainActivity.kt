package dev.tonholo.composeinstagram

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import dev.tonholo.composeinstagram.feature.home.HomeScreen
import dev.tonholo.composeinstagram.feature.profile.ProfileScreen
import dev.tonholo.composeinstagram.navigation.Route
import dev.tonholo.composeinstagram.navigation.Router
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

internal val LocalWindow = compositionLocalOf<Window> { throw IllegalArgumentException("LocalWindow not provided yet") }

class MainActivity : ComponentActivity() {
    private val router = Router()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalWindow provides window
            ) {
                ComposeInstagramTheme {
                    val currentRoute by router.currentRoute.collectAsState(Route.Home)
                    when (val route = currentRoute) {
                        Route.Home -> HomeScreen(navigateTo = router::navigateTo)

                        is Route.Profile -> ProfileScreen(user = route.user, navigateTo = router::navigateTo)
                    } // TODO: Handle scroll state when navigate.
                }
            }
        }
    }
}
