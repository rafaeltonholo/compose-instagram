package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.feature.user.UserProfileIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

private const val HEART_COMMENT_SUGGESTION = "❤️"
private const val RAISE_HANDS_COMMENT_SUGGESTION = "🙌"

@Composable
fun PostCommentBar(
    profileIconUrl: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onSuggestionClick: (String) -> Unit = {},
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UserProfileIcon(
            profileIconUrl = profileIconUrl,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Add a comment...",
            style = Theme.typography.bodySmall,
            color = Theme.colors.onBackgroundVariant,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = HEART_COMMENT_SUGGESTION,
            style = Theme.typography.bodySmall,
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable { onSuggestionClick(HEART_COMMENT_SUGGESTION) },
        )
        Text(
            text = RAISE_HANDS_COMMENT_SUGGESTION,
            style = Theme.typography.bodySmall,
            modifier = Modifier.clickable { onSuggestionClick(RAISE_HANDS_COMMENT_SUGGESTION) },
        )
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
        PostCommentBar(
            profileIconUrl = "mock",
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
