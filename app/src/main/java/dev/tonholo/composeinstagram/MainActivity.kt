package dev.tonholo.composeinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tonholo.composeinstagram.feature.home.HomeScreen
import dev.tonholo.composeinstagram.feature.profile.ProfileScreen
import dev.tonholo.composeinstagram.navigation.Route
import dev.tonholo.composeinstagram.navigation.Router
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

class MainActivity : ComponentActivity() {
    private val router = Router()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeInstagramTheme {
                val currentRoute by router.currentRoute.collectAsState(Route.Home)
                when (currentRoute) {
                    Route.Home -> HomeScreen(navigateTo = router::navigateTo)

                    Route.Profile -> ProfileScreen(navigateTo = router::navigateTo)
                }
            }
        }
    }
}
