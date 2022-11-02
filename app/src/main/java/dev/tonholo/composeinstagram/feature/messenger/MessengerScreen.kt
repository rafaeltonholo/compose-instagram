package dev.tonholo.composeinstagram.feature.messenger

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.messenger.components.MessengerAppBar
import dev.tonholo.composeinstagram.feature.messenger.components.MessengerHeader
import dev.tonholo.composeinstagram.feature.messenger.components.MessengerList
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessengerScreen(
    viewModel: MessengerViewModel = viewModel(),
    onNavigateBackClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    BackHandler(onBack = onNavigateBackClick)

    Scaffold(
        topBar = {
            MessengerAppBar(
                userTag = state.currentUser?.userTag ?: UserTag.EMPTY,
                onNavigateBackClick = onNavigateBackClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            MessengerHeader(
                searchTerm = state.searchTerm,
                modifier = Modifier.fillMaxWidth(),
                frequentlyUserMessages = state.frequentlyUserMessages ?: persistentSetOf(),
                onSearchChange = viewModel::onSearchChange,
                onRecentUserMessageClick = {},
            )
            MessengerList(
                messages = state.messages ?: persistentListOf(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            )
        }
    }
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun Preview() {
    ComposeInstagramTheme {
        MessengerScreen()
    }
}
