package dev.tonholo.composeinstagram.feature.home

import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.home.components.StoryItem
import kotlinx.collections.immutable.ImmutableList

data class UserState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val errorMessage: String? = null,
)

data class StoryState(
    val isLoading: Boolean = false,
    val storyItems: ImmutableList<StoryItem>? = null,
    val errorMessage: String? = null,
)

data class PostState(
    val isLoading: Boolean = false,
    val posts: ImmutableList<Post>? = null,
    val errorMessage: String? = null,
)

data class HomeUiState(
    val userState: UserState = UserState(),
    val storyState: StoryState = StoryState(),
    val postState: PostState = PostState(),
)
