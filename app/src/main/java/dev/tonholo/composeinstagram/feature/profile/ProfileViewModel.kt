package dev.tonholo.composeinstagram.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.profile.usecase.FetchProfileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val user: User,
    fetchProfileData: FetchProfileData = FetchProfileData(),
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            fetchProfileData(user)
                .collect { result ->
                    _state.value = when (result) {
                        is Result.Error -> {
                            throw NotImplementedError()
                        }

                        is Result.Loading -> ProfileUiState.Loading

                        is Result.Success -> with(result.data) {
                            ProfileUiState.MyProfile(
                                user = user,
                                bio = user.bio.orEmpty(),
                                hasStory = hasStory,
                                postsCount = 0,
                                followersCount = followers.size,
                                followingCount = following.size,
                            )
                        }
                    }
                }
        }

    }
}
