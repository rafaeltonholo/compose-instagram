package dev.tonholo.composeinstagram.feature.post

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.post.components.SneakPeekPostActionBar
import dev.tonholo.composeinstagram.feature.post.components.PostTitleBar
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

@Composable
fun SneakPeekPost(
    userTag: UserTag,
    profileImageUrl: String,
    image: String,
    isPostLiked: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        PostTitleBar(
            userTag = userTag,
            profileImageUrl = profileImageUrl,
            hasStory = false,
            modifier = Modifier.fillMaxWidth(),
        )
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 150.dp, 450.dp),
            contentScale = ContentScale.FillHeight,
        )
        SneakPeekPostActionBar(
            isPostLiked = isPostLiked,
            onLikeClick = { /*TODO*/ },
            onCommentClick = { /*TODO*/ },
            onShareClick = { /*TODO*/ },
            onOptionClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

private data class SneakPeekPostParams(
    val userTag: UserTag,
    val isPostLiked: Boolean,
)

private class SneakPeekPostParamsCol : CollectionPreviewParameterProvider<SneakPeekPostParams>(
    listOf(
        SneakPeekPostParams(
            userTag = UserTag("rafael"),
            isPostLiked = true,
        ),
        SneakPeekPostParams(
            userTag = UserTag("rtonholo"),
            isPostLiked = false,
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
    @PreviewParameter(SneakPeekPostParamsCol::class) params: SneakPeekPostParams,
) {
    ComposeInstagramTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            contentAlignment = Alignment.Center
        ) {
            SneakPeekPost(
                userTag = params.userTag,
                profileImageUrl = "any",
                image = "any",
                isPostLiked = params.isPostLiked,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
        }
    }
}
