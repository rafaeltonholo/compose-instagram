package dev.tonholo.composeinstagram.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.tonholo.composeinstagram.feature.home.components.HomeAppBar
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            HomeAppBar(modifier = Modifier.fillMaxWidth())
        }
    ) {
        Box(modifier = Modifier.padding(it).fillMaxSize())
    }
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun Preview() {
    ComposeInstagramTheme {
        HomeScreen()
    }
}
