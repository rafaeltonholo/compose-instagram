package dev.tonholo.composeinstagram.feature.post

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.tonholo.composeinstagram.domain.PostLike
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.extension.toTimestamp
import dev.tonholo.composeinstagram.feature.post.components.PostActionBar
import dev.tonholo.composeinstagram.feature.post.components.PostCommentBar
import dev.tonholo.composeinstagram.feature.post.components.PostCommentSection
import dev.tonholo.composeinstagram.feature.post.components.PostLikesBar
import dev.tonholo.composeinstagram.feature.post.components.PostTitleBar
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import java.time.LocalDateTime
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun ImagePost(
    userTag: UserTag,
    profileImageUrl: String,
    hasStory: Boolean,
    images: ImmutableList<String>,
    isPostLiked: Boolean,
    isPostSaved: Boolean,
    postDate: LocalDateTime,
    currentUserProfileImageUrl: String,
    modifier: Modifier = Modifier,
    likes: ImmutableSet<PostLike>? = null,
    ownerComment: String? = null,
    commentCount: Int = 0,
    onPostLiked: (liked: Boolean) -> Unit = { },
    onCommentClick: (suggestion: String?) -> Unit = {},
) {
    val pagerState = rememberPagerState()

    var shouldShowLikedIcon by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
    ) {
        PostTitleBar(
            userTag = userTag,
            profileImageUrl = profileImageUrl,
            hasStory = hasStory,
            modifier = Modifier.fillMaxWidth(),
        )
        Box {
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            shouldShowLikedIcon = true
                            onPostLiked(true)
                            coroutineScope.launch {
                                delay(500)
                                shouldShowLikedIcon = false
                            }
                        }
                    )
                }
            ) { index ->
                val image = images[index]
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, 450.dp),
                    contentScale = ContentScale.FillHeight,
                )
            }
            if (images.size > 1) {
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = Theme.colors.outline.copy(alpha = 0.5f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "${pagerState.currentPage + 1}/${images.size}",
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp),
                        style = Theme.typography.labelSmall,
                        color = Theme.colors.onOutline.copy(alpha = 0.8f),
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = shouldShowLikedIcon,
                enter = fadeIn(initialAlpha = 0.3f) + scaleIn(),
                exit = scaleOut() + fadeOut(),
                modifier = Modifier.align(Alignment.Center),
            ) {
                Image(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(Theme.colors.secondary),
                )
            }
        }
        PostActionBar(
            imageCount = images.size,
            isPostLiked = isPostLiked,
            isPostSaved = isPostSaved,
            onLikeClick = { onPostLiked(!isPostLiked) },
            onCommentClick = { onCommentClick(null) },
            onShareClick = { /*TODO*/ },
            onSaveClick = { /*TODO*/ },
            pagerState = pagerState,
        )
        if (likes != null) {
            PostLikesBar(
                likes = likes,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
            )
        }

        PostCommentSection(
            owner = userTag,
            ownerComment = ownerComment,
            commentCount = commentCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )

        PostCommentBar(
            profileIconUrl = currentUserProfileImageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { onCommentClick(null) },
            onSuggestionClick = onCommentClick
        )

        Text(
            text = "Posted at ${postDate.toTimestamp()}",
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp),
            style = Theme.typography.labelSmall,
            color = Theme.colors.onBackgroundVariant,
        )
    }
}

private data class ImagePostParams(
    val userTag: UserTag,
    val profileImageUrl: String,
    val hasStory: Boolean,
    val images: ImmutableList<String>,
    val isPostLiked: Boolean,
    val isPostSaved: Boolean,
    val likes: ImmutableSet<PostLike>?,
    val postDate: LocalDateTime,
    val ownerComment: String?,
    val commentCount: Int,
)

private class ImagePostParamsCol : CollectionPreviewParameterProvider<ImagePostParams>(
    listOf(
        ImagePostParams(
            userTag = UserTag("rafaeltonholo"),
            profileImageUrl = "mock",
            hasStory = true,
            images = persistentListOf("mock"),
            isPostLiked = true,
            isPostSaved = true,
            likes = null,
            postDate = LocalDateTime.parse("2022-09-04T10:15:30"),
            ownerComment = null,
            commentCount = 0,
        ),
        ImagePostParams(
            userTag = UserTag("rafaeltonholo"),
            profileImageUrl = "mock",
            hasStory = false,
            images = persistentListOf("mock", "mock"),
            isPostLiked = true,
            isPostSaved = true,
            likes = persistentSetOf(
                PostLike(
                    userTag = UserTag("rtonholo"),
                    profileImageUrl = "",
                ),
            ),
            postDate = LocalDateTime.now(),
            ownerComment = "That was nice!",
            commentCount = 100,
        ),
        ImagePostParams(
            userTag = UserTag("rafaeltonholo"),
            profileImageUrl = "mock",
            hasStory = false,
            images = persistentListOf("mock", "mock", "mock"),
            isPostLiked = false,
            isPostSaved = true,
            likes = persistentSetOf(
                PostLike(
                    userTag = UserTag("rtonholo"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("rafaeltonholo"),
                    profileImageUrl = "",
                ),
            ),
            postDate = LocalDateTime.now(),
            ownerComment = "😅😅😅",
            commentCount = 0,
        ),
        ImagePostParams(
            userTag = UserTag("rafaeltonholo"),
            profileImageUrl = "mock",
            hasStory = false,
            images = persistentListOf("mock", "mock", "mock", "mock"),
            isPostLiked = false,
            isPostSaved = false,
            likes = persistentSetOf(
                PostLike(
                    userTag = UserTag("rtonholo"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("rafaelt"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("rafaeltonholo"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("tonholo"),
                    profileImageUrl = "",
                ),
            ),
            postDate = LocalDateTime.now(),
            ownerComment = "LOL",
            commentCount = 1000,
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
    @PreviewParameter(ImagePostParamsCol::class) params: ImagePostParams,
) {
    val (
        userTag,
        profileImageUrl,
        hasStory,
        postCount,
        isPostLiked,
        isPostSaved,
        likes,
        postDate,
        ownerComment,
        commentCount,
    ) = params
    ComposeInstagramTheme {
        ImagePost(
            userTag,
            profileImageUrl,
            hasStory,
            postCount,
            isPostLiked,
            isPostSaved,
            postDate,
            currentUserProfileImageUrl = "mock",
            likes = likes,
            ownerComment = ownerComment,
            commentCount = commentCount,
        )
    }
}
