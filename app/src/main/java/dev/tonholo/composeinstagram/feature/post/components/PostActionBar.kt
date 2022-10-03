package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostActionBar(
    postCount: Int,
    isPostLiked: Boolean,
    isPostSaved: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
) {
    Row(modifier = modifier) {
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
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share post",
                tint = Theme.colors.onSurface,
            )
        }
        if (postCount > 1) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center,
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    pageCount = postCount,
                    activeColor = Theme.colors.onSurface,
                    inactiveColor = Theme.colors.onSurface.copy(alpha = 0.5f),
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
        IconButton(onClick = onSaveClick) {
            Icon(
                imageVector = if (isPostSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkAdd,
                contentDescription = "Save post",
                tint = Theme.colors.onSurface,
            )
        }
    }
}

private data class PostActionBarParams(
    val postCount: Int,
    val isPostLiked: Boolean,
    val isPostSaved: Boolean,
)

private class PostActionBarParamsCol : CollectionPreviewParameterProvider<PostActionBarParams>(
    listOf(
        PostActionBarParams(
            postCount = 1,
            isPostLiked = false,
            isPostSaved = false,
        ),
        PostActionBarParams(
            postCount = 2,
            isPostLiked = true,
            isPostSaved = false,
        ),
        PostActionBarParams(
            postCount = 5,
            isPostLiked = false,
            isPostSaved = true,
        ),
        PostActionBarParams(
            postCount = 10,
            isPostLiked = true,
            isPostSaved = true,
        ),
    )
)

@OptIn(ExperimentalPagerApi::class)
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
    @PreviewParameter(PostActionBarParamsCol::class) postActionBarParams: PostActionBarParams,
) {
    ComposeInstagramTheme {
        PostActionBar(
            postCount = postActionBarParams.postCount,
            isPostLiked = postActionBarParams.isPostLiked,
            isPostSaved = postActionBarParams.isPostSaved,
            onLikeClick = { },
            onCommentClick = { },
            onShareClick = { },
            onSaveClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
