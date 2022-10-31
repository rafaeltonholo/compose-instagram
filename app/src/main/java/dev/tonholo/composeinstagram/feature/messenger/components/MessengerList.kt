package dev.tonholo.composeinstagram.feature.messenger.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessengerList(
    messages: ImmutableList<MessageItemState>,
    modifier: Modifier = Modifier,
    onRequestsClick: () -> Unit = {},
    onMessageClick: (UserTag) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        stickyHeader {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Theme.colors.background),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Messages",
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.onBackground,
                )
                TextButton(onClick = onRequestsClick) {
                    Text(
                        text = "Requests",
                        style = Theme.typography.titleMedium,
                        color = Theme.colors.primary,
                    )
                }
            }
        }

        if (messages.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No messages",
                        color = Theme.colors.onBackground,
                        textAlign = TextAlign.Center,
                        style = Theme.typography.displaySmall,
                    )
                }
            }
        }

        items(messages) { message ->
            MessageItem(
                message = message,
                modifier = Modifier.padding(vertical = 4.dp),
                onClick = onMessageClick,
            )
        }
    }
}

private class MessengerListParamCol : CollectionPreviewParameterProvider<ImmutableList<MessageItemState>>(
    listOf(
        persistentListOf(),
        FakeData.messages.take(1).map {
            MessageItemState(
                senderUserTag = it.from.userTag,
                senderProfileImageUrl = it.from.profileImage,
                senderName = it.from.name,
                senderHasStory = false,
                messageContent = it.content,
                sentAt = "",
                isNotRead = false,
            )
        }.toImmutableList(),
        FakeData.messages.take(20).map {
            MessageItemState(
                senderUserTag = it.from.userTag,
                senderProfileImageUrl = it.from.profileImage,
                senderName = it.from.name,
                senderHasStory = false,
                messageContent = it.content,
                sentAt = "",
                isNotRead = false,
            )
        }.toImmutableList(),
    )
)

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
private fun Preview(
    @PreviewParameter(MessengerListParamCol::class) messages: ImmutableList<MessageItemState>,
) {
    ComposeInstagramTheme {
        MessengerList(
            messages = messages,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
