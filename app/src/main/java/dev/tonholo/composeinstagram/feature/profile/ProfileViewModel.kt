package dev.tonholo.composeinstagram.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.profile.usecase.FetchProfileData
import dev.tonholo.composeinstagram.feature.profile.usecase.FetchProfilePosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ProfileViewModel(
    private val user: User,
    fetchProfileData: FetchProfileData = FetchProfileData(),
    fetchProfilePosts: FetchProfilePosts = FetchProfilePosts(),
    userDao: UserDao = FakeUserDao,
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val state = _state.asStateFlow()

    init {
        fetchProfilePosts(user)
            .map { result ->
                when (result) {
                    is Result.Success -> result.data
                    else -> emptyList()
                }
            }
            .combine(fetchProfileData(user)) { posts, result ->
                when (result) {
                    is Result.Error -> {
                        throw NotImplementedError()
                    }

                    is Result.Loading -> ProfileUiState.Loading

                    is Result.Success -> with(result.data) {
                        val currentUser = userDao.getCurrentUser()
                        if (user == currentUser) {
                            ProfileUiState.MyProfile(
                                user = user,
                                bio = user.bio.orEmpty(),
                                hasStory = hasStory,
                                posts = posts,
                                followersCount = followers.size,
                                followingCount = following.size,
                            )
                        } else {
                            ProfileUiState.OtherProfile(
                                user = user,
                                bio = user.bio.orEmpty(),
                                hasStory = hasStory,
                                posts = posts,
                                followersCount = followers.size,
                                followingCount = following.size,
                                isFollowing = userDao.getFollowers(user).any { it == currentUser },
                                isPrivate = user.isPrivate,
                            )
                        }
                    }
                }
            }
            .onEach { state -> _state.value = state }
            .launchIn(viewModelScope)
    }
}
