package dev.tonholo.composeinstagram.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.feature.home.components.HomeAppBar
import dev.tonholo.composeinstagram.feature.home.components.StoryRow
import dev.tonholo.composeinstagram.feature.home.usercase.FetchStories
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel {
        HomeViewModel(FetchStories(FakeStoryApi))
    }
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            HomeAppBar(modifier = Modifier.fillMaxWidth())
        },

    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                state.storyState.storyItems?.let { stories ->
                    StoryRow(
                        storyItems = stories,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                }
            }
        }
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
