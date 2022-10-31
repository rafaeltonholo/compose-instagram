package dev.tonholo.composeinstagram.domain

import androidx.compose.runtime.Stable

@Stable
data class User(
    val profileImage: String,
    val name: String,
    @Stable val userTag: UserTag,
)
