package dev.tonholo.composeinstagram.feature.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.fake.remote.FakePostApi
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.feature.home.components.HomeAppBar
import dev.tonholo.composeinstagram.feature.home.components.HomeContent
import dev.tonholo.composeinstagram.feature.home.components.NavigationBottomBar
import dev.tonholo.composeinstagram.feature.home.usecase.FetchHomePosts
import dev.tonholo.composeinstagram.feature.home.usecase.FetchStories
import dev.tonholo.composeinstagram.feature.home.usecase.FetchUserData
import dev.tonholo.composeinstagram.feature.home.usecase.LikePost
import dev.tonholo.composeinstagram.feature.messenger.MessengerScreen
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import kotlinx.coroutines.launch

private const val STORY_PAGE = 0
private const val MAIN_PAGE = 1
private const val MESSENGER_PAGE = 2

@OptIn(ExperimentalPagerApi::class)
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
    val pagerState = rememberPagerState(initialPage = MAIN_PAGE)
    val coroutineScope = rememberCoroutineScope()

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

    HorizontalPager(
        count = 3,
        state = pagerState,
    ) { page ->
        when (page) {
            STORY_PAGE -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            )

            MAIN_PAGE -> Scaffold(
                topBar = {
                    HomeAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        onMessengerIconClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(MESSENGER_PAGE)
                            }
                        }
                    )
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

            MESSENGER_PAGE -> MessengerScreen(
                onNavigateBackClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(MAIN_PAGE)
                    }
                }
            )
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
