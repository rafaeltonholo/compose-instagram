package dev.tonholo.composeinstagram.data.remote

import dev.tonholo.composeinstagram.domain.Post

interface PostApi {
    suspend fun getHomePosts(
        cursor: Int,
        limit: Int,
    ): List<Post>

    suspend fun updatePost(post: Post): Boolean
}
