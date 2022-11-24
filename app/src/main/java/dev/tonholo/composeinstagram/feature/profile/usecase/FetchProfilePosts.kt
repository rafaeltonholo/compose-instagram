package dev.tonholo.composeinstagram.feature.profile.usecase

import android.util.Log
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.remote.FakePostApi
import dev.tonholo.composeinstagram.data.remote.PostApi
import dev.tonholo.composeinstagram.domain.User
import kotlinx.coroutines.flow.flow

private const val TAG = "FetchProfilePosts"
class FetchProfilePosts(
    private val postApi: PostApi = FakePostApi,
) {
    operator fun invoke(user: User) = flow {
        emit(Result.Loading())
        try {
            val posts = postApi.getPosts(user)
            emit(Result.Success(posts))
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching profile posts", e)
            emit(
                Result.Error(
                    message = e.message ?: "Unhandled error",
                    throwable = e,
                )
            )
        }
    }
}
