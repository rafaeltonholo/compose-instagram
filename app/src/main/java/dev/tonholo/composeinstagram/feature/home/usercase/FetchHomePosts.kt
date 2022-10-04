package dev.tonholo.composeinstagram.feature.home.usercase

import android.util.Log
import dev.tonholo.composeinstagram.data.remote.PostApi
import dev.tonholo.composeinstagram.domain.Post
import dev.tonholo.composeinstagram.common.Result
import kotlinx.coroutines.flow.flow

private const val MAX_PAGE_POST = 10
private const val TAG = "FetchHomePosts"

class FetchHomePosts(
    private val postApi: PostApi,
) {
    private var currentCursor = 0

    operator fun invoke(currentPosts: List<Post>? = null) = flow {
        emit(Result.Loading(currentPosts))

        try {
            val homePosts = postApi.getHomePosts(currentCursor, MAX_PAGE_POST)
            currentCursor += MAX_PAGE_POST
            emit(Result.Success(homePosts))
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching home posts", e)
            emit(Result.Error(
                message = e.message ?: "Unhandled error",
                throwable = e,
                data = currentPosts,
            ))
        }
    }
}
