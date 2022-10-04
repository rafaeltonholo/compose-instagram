package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.user.UserStoryIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun PostTitleBar(
    userTag: UserTag,
    profileImageUrl: String,
    hasStory: Boolean,
    modifier: Modifier = Modifier,
    onStoryClick: () -> Unit = {},
    onUserTagClick: (UserTag) -> Unit = {},
    onOptionsClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        UserStoryIcon(
            profileImageUrl = profileImageUrl,
            hasStory = hasStory,
            modifier = Modifier
                .size(32.dp),
            shouldShowAddIcon = false,
            storyBorderStrokeWidth = 1.dp,
            onClick = onStoryClick,
        )
        Spacer(modifier = Modifier.width(8.dp))
        ClickableText(
            text = buildAnnotatedString {
                addStyle(SpanStyle(color = Theme.colors.onSurface), 0, userTag.tag.length)
                append(userTag.tag)
            },
            style = Theme.typography.labelMedium,
            onClick = { onUserTagClick(userTag) },
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onOptionsClick) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Post Options",
                tint = Theme.colors.onSurface,
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
        Column {
            PostTitleBar(
                userTag = UserTag("rafaeltonholo"),
                profileImageUrl = "mock",
                hasStory = true,
            )
            Divider()
            PostTitleBar(
                userTag = UserTag("rafaeltonholo"),
                profileImageUrl = "mock",
                hasStory = false,
            )
        }
    }
}
