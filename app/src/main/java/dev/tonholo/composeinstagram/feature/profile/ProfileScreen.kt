package dev.tonholo.composeinstagram.feature.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.profile.components.PrivateProfilePostSection
import dev.tonholo.composeinstagram.feature.profile.components.ProfileActionsSection
import dev.tonholo.composeinstagram.feature.profile.components.ProfileAppBar
import dev.tonholo.composeinstagram.feature.profile.components.ProfileHeader
import dev.tonholo.composeinstagram.navigation.Route
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlinx.coroutines.launch

private const val TAB_POSTS = 0
private const val TAB_REELS = 1
private const val TAB_TAGGED_POSTS = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User,
    viewModel: ProfileViewModel = viewModel(key = user.userTag.tag) {
        ProfileViewModel(user)
    },
    navigateTo: suspend (Route) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val canShowPosts by remember {
        derivedStateOf {
            val currentState = state as? ProfileUiState.OtherProfile
            when {
                currentState == null -> true
                currentState.isFollowing -> true
                !currentState.isPrivate -> true
                else -> false
            }
        }
    }

    BackHandler {
        coroutineScope.launch { navigateTo(Route.Home) }
    }

    Scaffold(
        topBar = {
            ProfileAppBar(userTag = state.user?.userTag, isPrivateAccount = state.user?.isPrivate == true)
        }
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item(span = { GridItemSpan(3) }) {
                ProfileHeader(
                    userName = state.user?.name.orEmpty(),
                    userBio = state.bio,
                    profileImageUrl = state.user?.profileImage.orEmpty(),
                    hasStory = state.hasStory,
                    postsCount = state.posts.size,
                    followersCount = state.followersCount,
                    followingCount = state.followingCount,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }

            item(span = { GridItemSpan(3) }) {
                ProfileActionsSection(
                    state = state,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }

            item(span = { GridItemSpan(3) }) { // TODO: Make it sticky
                var selectedTabIndex by remember { mutableStateOf(TAB_POSTS) }
                val tabIconModifier = Modifier.padding(12.dp)
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            height = 1.dp,
                            color = Theme.colors.onSurfaceVariant,
                        )
                    },
                ) {
                    Tab(selected = selectedTabIndex == TAB_POSTS, onClick = { selectedTabIndex = TAB_POSTS }) {
                        Icon(
                            imageVector = Icons.Outlined.GridOn,
                            contentDescription = "Posts",
                            tint = Theme.colors.onBackground,
                            modifier = tabIconModifier,
                        )
                    }
                    Tab(selected = selectedTabIndex == TAB_REELS, onClick = { selectedTabIndex = TAB_POSTS }) {
                        Icon(
                            imageVector = Icons.Outlined.Subscriptions,
                            contentDescription = "Reels",
                            tint = Theme.colors.onBackground,
                            modifier = tabIconModifier,
                        )
                    }
                    Tab(selected = selectedTabIndex == TAB_TAGGED_POSTS, onClick = { selectedTabIndex = TAB_POSTS }) {
                        Icon(
                            imageVector = Icons.Outlined.PersonPin,
                            contentDescription = "Tagged posts",
                            tint = Theme.colors.onBackground,
                            modifier = tabIconModifier,
                        )
                    }
                }
            }

            if (canShowPosts) {
                itemsIndexed(
                    items = state.posts,
                    key = { _, post -> post.id },
                ) { index, post ->
                    AsyncImage(
                        model = post.images.first(),
                        contentDescription = post.comments.orEmpty().firstOrNull()?.text.orEmpty(),
                        modifier = Modifier
                            .size(128.dp)
                            .padding(bottom = 1.dp)
                            .then(
                                if (index % 3 == 0) {
                                    Modifier
                                } else {
                                    Modifier.padding(start = 1.dp)
                                }
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            } else if (state.user?.isPrivate == true) {
                item(span = { GridItemSpan(3) }) {
                    PrivateProfilePostSection(
                        modifier = Modifier.padding(top = 32.dp),
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
        ProfileScreen(
            user = FakeData.currentUser,
        )
    }
}
