package dev.tonholo.composeinstagram.domain

import java.time.LocalDateTime

data class PostLike(
    val userTag: UserTag,
    val profileImageUrl: String,
)

data class Post(
    val owner: User,
    val images: List<String>,
    val likes: Set<PostLike>,
    val comments: List<Comment>?,
    val postDate: LocalDateTime,
    val ownerComment: String?,
)
