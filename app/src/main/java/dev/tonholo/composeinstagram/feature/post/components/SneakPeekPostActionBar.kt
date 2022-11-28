package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun SneakPeekPostActionBar(
    isPostLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onOptionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = if (isPostLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Like post",
                tint = if (isPostLiked) Theme.colors.secondary else Theme.colors.onSurface
            )
        }
        IconButton(onClick = onCommentClick) {
            Icon(
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Comment post",
                tint = Theme.colors.onSurface,
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Outlined.Send,
                contentDescription = "Share post",
                tint = Theme.colors.onSurface,
            )
        }

        IconButton(onClick = onOptionClick) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Options",
                tint = Theme.colors.onSurface,
            )
        }
    }
}

private class SneakPeekPostActionBarParamsCol : CollectionPreviewParameterProvider<Boolean>(
    listOf(
        true,
        false,
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
    @PreviewParameter(SneakPeekPostActionBarParamsCol::class) isPostLiked: Boolean,
) {
    ComposeInstagramTheme {
        SneakPeekPostActionBar(
            isPostLiked = isPostLiked,
            onLikeClick = { },
            onCommentClick = { },
            onShareClick = { },
            onOptionClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
