package dev.tonholo.composeinstagram.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.feature.home.usercase.FetchHomePosts
import dev.tonholo.composeinstagram.feature.home.usercase.FetchStories
import dev.tonholo.composeinstagram.feature.home.usercase.FetchUserData
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    fetchUserData: FetchUserData,
    fetchStories: FetchStories,
    private val fetchHomePosts: FetchHomePosts,
) : ViewModel() {
    private val postState = MutableStateFlow(PostState())

    val state: StateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
        .combine(fetchUserData()) { current, result ->
            val newUserState = when (result) {
                is Result.Error -> current.userState.copy(
                    isLoading = false,
                    errorMessage = result.message,
                )

                is Result.Loading -> current.userState.copy(
                    isLoading = true,
                )

                is Result.Success -> current.userState.copy(
                    isLoading = false,
                    currentUser = result.data,
                )
            }
            current.copy(userState = newUserState)
        }
        .combine(fetchStories()) { current, stories ->
            val currentStoryState = current.storyState
            val newStoryState = when (stories) {
                is Result.Error -> currentStoryState.copy(
                    isLoading = false,
                    errorMessage = stories.message,
                )

                is Result.Loading -> currentStoryState.copy(
                    isLoading = true,
                    storyItems = stories.data?.toImmutableList(),
                )

                is Result.Success -> currentStoryState.copy(
                    isLoading = false,
                    storyItems = stories.data.toImmutableList(),
                )
            }
            current.copy(storyState = newStoryState)
        }
        .combine(postState) { current, newPostState ->
            current.copy(postState = newPostState)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeUiState())

    init {
        fetchNextPosts()
    }

    private fun fetchNextPosts() {
        viewModelScope.launch {
            fetchHomePosts(postState.value.posts).collectLatest { result ->
                postState.update {
                    when (result) {
                        is Result.Error -> it.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            posts = result.data?.toImmutableList(),
                        )

                        is Result.Loading -> it.copy(
                            isLoading = true,
                            posts = result.data?.toImmutableList(),
                            errorMessage = null,
                        )

                        is Result.Success -> it.copy(
                            isLoading = false,
                            posts = (it.posts.orEmpty() + result.data).toImmutableList(),
                            errorMessage = null,
                        )
                    }
                }
            }
        }
    }
}
