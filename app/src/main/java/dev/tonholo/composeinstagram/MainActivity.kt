package dev.tonholo.composeinstagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.tonholo.composeinstagram.feature.home.HomeScreen
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeInstagramTheme {
                HomeScreen()
            }
        }
    }
}
