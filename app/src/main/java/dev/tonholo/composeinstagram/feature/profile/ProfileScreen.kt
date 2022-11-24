package dev.tonholo.composeinstagram.feature.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.profile.components.ProfileActionsSection
import dev.tonholo.composeinstagram.feature.profile.components.ProfileAppBar
import dev.tonholo.composeinstagram.feature.profile.components.ProfileHeader
import dev.tonholo.composeinstagram.navigation.Route
import kotlinx.coroutines.launch

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

    BackHandler {
        coroutineScope.launch { navigateTo(Route.Home) }
    }

    Scaffold(
        topBar = {
            ProfileAppBar(userTag = state.user?.userTag, isPrivateAccount = false)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                ProfileHeader(
                    userName = state.user?.name.orEmpty(),
                    userBio = state.bio,
                    profileImageUrl = state.user?.profileImage.orEmpty(),
                    hasStory = state.hasStory,
                    postsCount = state.postsCount,
                    followersCount = state.followersCount,
                    followingCount = state.followingCount,
                )
            }

            item {
                ProfileActionsSection(state = state)
            }
        }
    }
}
