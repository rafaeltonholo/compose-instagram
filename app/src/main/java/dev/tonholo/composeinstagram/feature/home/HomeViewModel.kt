package dev.tonholo.composeinstagram.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.fake.remote.FakePostApi
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.data.local.NotificationService
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.home.usecase.FetchHomePosts
import dev.tonholo.composeinstagram.feature.home.usecase.FetchStories
import dev.tonholo.composeinstagram.feature.home.usecase.FetchUserData
import dev.tonholo.composeinstagram.feature.home.usecase.LikePost
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel(
    fetchUserData: FetchUserData = FetchUserData(FakeUserDao),
    fetchStories: FetchStories = FetchStories(FakeStoryApi),
    private val fetchHomePosts: FetchHomePosts = FetchHomePosts(FakePostApi),
    private val likePost: LikePost = LikePost(FakePostApi, FakeUserDao),
    private val notificationService: NotificationService = NotificationService(),
    private val userDao: UserDao = FakeUserDao,
) : ViewModel() {
    private val postState = MutableStateFlow(PostState())
    private val postLikeState = MutableStateFlow(PostLikeState())

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

            val messagesCount = if (result is Result.Success) {
                notificationService.getNonReadMessageCount(result.data)
            } else 0

            current.copy(
                messagesCount = messagesCount,
                userState = newUserState,
            )
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
        .combine(postLikeState) { current, newPostLikeState ->
            current.copy(postLikeState = newPostLikeState)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeUiState())

    private val _events = MutableSharedFlow<HomeScreenEvent>(extraBufferCapacity = 10)
    val events = _events.asSharedFlow()

    init {
        fetchNextPosts()
    }

    fun fetchNextPosts() {
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
                            isEndReached = result.data.isEmpty(),
                        )
                    }
                }
            }
        }
    }

    fun onPostLiked(liked: Boolean, post: Post) {
        viewModelScope.launch {
            likePost(liked = liked, post = post).collectLatest { result ->
                postLikeState.update { state ->
                    when (result) {
                        is Result.Error -> state.copy(
                            isLoading = false,
                            errorMessage = result.message,
                        )

                        is Result.Loading -> state.copy(
                            isLoading = true,
                            errorMessage = null,
                        )

                        is Result.Success -> {
                            val posts = postState.value.posts.orEmpty().toMutableList()
                            val index = posts.indexOfFirst { it.id == post.id }
                            posts[index] = result.data
                            postState.update { it.copy(posts = posts.toImmutableList()) }
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onCommentClick(suggestion: String?) {
        Log.d(TAG, "onCommentClick() called with: suggestion = $suggestion")
    }

    fun onUserTagClick(userTag: UserTag) {
        viewModelScope.launch {
            userDao.getUser(userTag)?.let { user ->
                navigateToProfile(user)
            }
        }
    }

    fun onUserProfileClick() {
        state.value.userState.currentUser?.let { user ->
            navigateToProfile(user)
        }
    }

    private fun navigateToProfile(user: User) {
        _events.tryEmit(HomeScreenEvent.NavigateToProfile(user))
    }
}

sealed interface HomeScreenEvent {
    object None : HomeScreenEvent

    data class NavigateToProfile(val user: User) : HomeScreenEvent
}
