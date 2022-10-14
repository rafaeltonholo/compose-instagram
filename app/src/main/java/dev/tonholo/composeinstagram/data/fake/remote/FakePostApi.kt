package dev.tonholo.composeinstagram.data.fake.remote

import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.data.remote.PostApi
import dev.tonholo.composeinstagram.domain.Post

object FakePostApi : PostApi {
    override suspend fun getHomePosts(
        cursor: Int,
        limit: Int,
    ): List<Post> {
        return FakeData.posts
            .sortedByDescending { it.postDate }
            .drop(cursor)
            .take(10)
    }

    override suspend fun updatePost(post: Post): Boolean {
        val index = FakeData.posts.indexOfFirst { storedPost -> storedPost.id == post.id }
        FakeData.posts[index] = post
        return true
    }
}
