package dev.tonholo.composeinstagram.domain

data class UserFollow(
    val user: User,
    val follow: User,
)
