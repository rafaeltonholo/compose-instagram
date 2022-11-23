package dev.tonholo.composeinstagram.feature.profile

import dev.tonholo.composeinstagram.domain.User

sealed class ProfileUiState(
    open val user: User? = null,
    open val bio: String = "",
    open val hasStory: Boolean = false,
    open val postsCount: Int = 0,
    open val followersCount: Int = 0,
    open val followingCount: Int = 0,
) {
    object Loading : ProfileUiState()

    data class MyProfile(
        override val user: User,
        override val bio: String,
        override val hasStory: Boolean,
        override val postsCount: Int,
        override val followersCount: Int,
        override val followingCount: Int,
    ) : ProfileUiState(user, bio, hasStory, postsCount, followersCount, followingCount)

    data class OtherProfile(
        override val user: User,
        override val bio: String,
        override val hasStory: Boolean,
        override val postsCount: Int,
        override val followersCount: Int,
        override val followingCount: Int,
        val isFollowing: Boolean,
        val isPrivate: Boolean,
    ) : ProfileUiState(user, bio, hasStory, postsCount, followersCount, followingCount)
}
