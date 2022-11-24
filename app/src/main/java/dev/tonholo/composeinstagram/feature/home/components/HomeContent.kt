package dev.tonholo.composeinstagram.feature.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.post.ImagePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun HomeContent(
    storyItems: ImmutableList<StoryItem>?,
    posts: ImmutableList<Post>?,
    isEndReached: Boolean,
    isLoading: Boolean,
    currentUserTag: UserTag?,
    currentUserProfileImage: String,
    onFetchNextPost: () -> Unit,
    onPostLiked: (Boolean, Post) -> Unit,
    onCommentClick: (String?) -> Unit,
    onUserTagClick: (UserTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            storyItems?.let { stories ->
                StoryRow(
                    storyItems = stories,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                )
            }
        }

        posts?.let { posts ->
            itemsIndexed(posts) { index, post ->
                LaunchedEffect(index >= posts.size - 1) {
                    if (index >= posts.size - 1 && !isEndReached && !isLoading) {
                        onFetchNextPost()
                    }
                }

                ImagePost(
                    userTag = post.owner.userTag,
                    profileImageUrl = post.owner.profileImage,
                    hasStory = false,
                    images = post.images.toImmutableList(),
                    isPostLiked = post.likes.any { like -> like.userTag == currentUserTag },
                    isPostSaved = false,
                    postDate = post.postDate,
                    currentUserProfileImageUrl = currentUserProfileImage,
                    likes = post.likes.toImmutableSet(),
                    ownerComment = post.ownerComment,
                    commentCount = post.comments?.size ?: 0,
                    onPostLiked = { liked ->
                        onPostLiked(liked, post)
                    },
                    onCommentClick = onCommentClick,
                    onUserTagClick = onUserTagClick,
                )
            }
        }
    }

}
