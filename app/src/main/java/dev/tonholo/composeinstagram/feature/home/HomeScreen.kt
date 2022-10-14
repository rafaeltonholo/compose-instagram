package dev.tonholo.composeinstagram.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.fake.remote.FakePostApi
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.feature.home.components.HomeAppBar
import dev.tonholo.composeinstagram.feature.home.components.StoryRow
import dev.tonholo.composeinstagram.feature.home.usercase.FetchHomePosts
import dev.tonholo.composeinstagram.feature.home.usercase.FetchStories
import dev.tonholo.composeinstagram.feature.home.usercase.FetchUserData
import dev.tonholo.composeinstagram.feature.home.usercase.LikePost
import dev.tonholo.composeinstagram.feature.post.ImagePost
import dev.tonholo.composeinstagram.feature.post.components.NavigationBottomBar
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel {
        HomeViewModel(
            FetchUserData(FakeUserDao),
            FetchStories(FakeStoryApi),
            FetchHomePosts(FakePostApi),
            LikePost(FakePostApi, FakeUserDao),
        )
    },
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(state.postLikeState) {
        if (state.postLikeState.errorMessage?.isNotEmpty() == true) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = state.postLikeState.errorMessage.orEmpty(),
            )
        }
    }
    LaunchedEffect(state.postState) {
        if (state.postState.errorMessage?.isNotEmpty() == true) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = state.postState.errorMessage.orEmpty(),
            )
        }
    }

    Scaffold(
        topBar = {
            HomeAppBar(modifier = Modifier.fillMaxWidth())
        },
        bottomBar = {
            NavigationBottomBar(
                profileIconUrl = state.userState.currentUser?.profileImage.orEmpty(),
                onSearchClick = { /*TODO*/ },
                onReelsClick = { /*TODO*/ },
                onShopClick = { /*TODO*/ },
                onUserProfileClick = { /*TODO*/ }
            )
        },
        scaffoldState = scaffoldState,
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            item {
                state.storyState.storyItems?.let { stories ->
                    StoryRow(
                        storyItems = stories,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                    )
                }
            }

            state.postState.posts?.let { posts ->
                itemsIndexed(posts) { index, post ->
                    LaunchedEffect(index >= posts.size - 1) {
                        if (index >= posts.size - 1 && !state.postState.isEndReached && !state.postState.isLoading) {
                            viewModel.fetchNextPosts()
                        }
                    }

                    ImagePost(
                        userTag = post.owner.userTag,
                        profileImageUrl = post.owner.profileImage,
                        hasStory = false,
                        images = post.images.toImmutableList(),
                        isPostLiked = post.likes.any { like -> like.userTag == state.userState.currentUser?.userTag },
                        isPostSaved = false,
                        likes = post.likes.toImmutableSet(),
                        postDate = post.postDate,
                        ownerComment = post.ownerComment,
                        commentCount = post.comments?.size ?: 0,
                        onPostLiked = { liked ->
                            viewModel.onPostLiked(liked, post)
                        },
                    )
                }
            }
        }
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
        HomeScreen()
    }
}
