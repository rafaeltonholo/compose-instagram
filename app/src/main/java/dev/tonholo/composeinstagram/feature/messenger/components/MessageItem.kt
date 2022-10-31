package dev.tonholo.composeinstagram.feature.messenger.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.user.UserStoryIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

data class MessageItemState(
    @Stable val senderUserTag: UserTag,
    val senderProfileImageUrl: String,
    val senderName: String,
    val senderHasStory: Boolean,
    val messageContent: String,
    val sentAt: String,
    val isNotRead: Boolean,
)

@Composable
fun MessageItem(
    message: MessageItemState,
    modifier: Modifier = Modifier,
    onClick: (UserTag) -> Unit = {},
) {
    Row(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick(message.senderUserTag)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserStoryIcon(
            profileImageUrl = message.senderProfileImageUrl,
            hasStory = message.senderHasStory,
            modifier = Modifier.size(48.dp),
            shouldShowAddIcon = false,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = message.senderName,
                color = Theme.colors.onBackground,
                style = Theme.typography.labelMedium,
            )
            Row(
                modifier = Modifier.wrapContentWidth(),
            ) {
                Text(
                    text = message.messageContent,
                    overflow = TextOverflow.Ellipsis,
                    color = Theme.colors.onBackground.copy(
                        alpha = if (message.isNotRead) 1f else 0.5f
                    ),
                    maxLines = 1,
                    style = Theme.typography.labelMedium,
                )
                Text(
                    text = " - ${message.sentAt}",
                    overflow = TextOverflow.Ellipsis,
                    color = Theme.colors.onBackground.copy(
                        alpha = if (message.isNotRead) 1f else 0.5f
                    ),
                    maxLines = 1,
                    modifier = Modifier.weight(1f), // TODO: Fix show when text content overflow.
                    style = Theme.typography.labelMedium,
                )
            }
        }

        if (message.isNotRead) {
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = Theme.colors.primary, shape = CircleShape),
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "Send picture",
                tint = Theme.colors.onBackground,
            )
        }
    }
}

private class MessageItemPrevParams : CollectionPreviewParameterProvider<MessageItemState>(
    listOf(
        MessageItemState(
            senderUserTag = UserTag("mock"),
            senderProfileImageUrl = "mock",
            senderName = "Rafael Tonholo",
            senderHasStory = true,
            messageContent = "That is a message",
            sentAt = "now",
            isNotRead = true,
        ),
        MessageItemState(
            senderUserTag = UserTag("mock"),
            senderProfileImageUrl = "mock",
            senderName = "Rafael Tonholo",
            senderHasStory = false,
            messageContent = "That is a message",
            sentAt = "1 h",
            isNotRead = false,
        ),
        MessageItemState(
            senderUserTag = UserTag("mock"),
            senderProfileImageUrl = "mock",
            senderHasStory = true,
            senderName = "Rafael Tonholo",
            messageContent = "That is a message ".repeat(1000),
            sentAt = "1 y",
            isNotRead = false,
        ),
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
    @PreviewParameter(MessageItemPrevParams::class) state: MessageItemState,
) {
    ComposeInstagramTheme {
        MessageItem(
            message = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
