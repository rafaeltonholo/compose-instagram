package dev.tonholo.composeinstagram.domain

// TODO: Accept comments in comments.
data class Comment(
    val owner: User,
    val post: Post,
    val text: String,
    val likes: Int,
)
