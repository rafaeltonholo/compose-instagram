package dev.tonholo.composeinstagram.feature.home.usecase

import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.data.remote.PostApi
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.domain.PostLike
import java.lang.Exception
import kotlinx.coroutines.flow.flow

class LikePost(
    private val postApi: PostApi,
    private val userDao: UserDao,
) {

    operator fun invoke(liked: Boolean, post: Post) = flow {
        emit(Result.Loading())
        try {
            val currentUser = userDao.getCurrentUser()
            val likes = if (liked) {
                post.likes + setOf(
                    PostLike(
                        userTag = currentUser.userTag,
                        profileImageUrl = currentUser.profileImage,
                    )
                )
            } else {
                post.likes.toMutableSet().also { likes ->
                    likes.removeIf { postLike -> postLike.userTag == currentUser.userTag }
                }
            }

            val newPost = post.copy(likes = likes)
            val success = postApi.updatePost(newPost)
            if (success) {
                emit(Result.Success(newPost))
            } else {
                emit(Result.Error("Could not add/remove like from post."))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Unhandled error", e))
        }
    }
}
