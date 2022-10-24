package dev.tonholo.composeinstagram.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.fake.remote.FakePostApi
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.feature.home.components.HomeAppBar
import dev.tonholo.composeinstagram.feature.home.components.HomeContent
import dev.tonholo.composeinstagram.feature.home.components.NavigationBottomBar
import dev.tonholo.composeinstagram.feature.home.usercase.FetchHomePosts
import dev.tonholo.composeinstagram.feature.home.usercase.FetchStories
import dev.tonholo.composeinstagram.feature.home.usercase.FetchUserData
import dev.tonholo.composeinstagram.feature.home.usercase.LikePost
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme

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
        HomeContent(
            storyItems = state.storyState.storyItems,
            posts = state.postState.posts,
            isEndReached = state.postState.isEndReached,
            isLoading = state.postState.isLoading,
            currentUserTag = state.userState.currentUser?.userTag,
            currentUserProfileImage = state.userState.currentUser?.profileImage.orEmpty(),
            onFetchNextPost = viewModel::fetchNextPosts,
            onPostLiked = viewModel::onPostLiked,
            onCommentClick = viewModel::onCommentClick,
            modifier = Modifier
                .padding(it),
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
        HomeScreen()
    }
}
