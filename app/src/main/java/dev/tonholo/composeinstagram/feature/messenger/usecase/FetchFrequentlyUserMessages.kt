package dev.tonholo.composeinstagram.feature.messenger.usecase

import android.util.Log
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.remote.FakeMessengerApi
import dev.tonholo.composeinstagram.data.remote.MessengerApi
import dev.tonholo.composeinstagram.domain.User
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.flow

private const val TAG = "FetchFrequentlyUserMess"

class FetchFrequentlyUserMessages(
    private val messengerApi: MessengerApi = FakeMessengerApi,
) {
    operator fun invoke(currentUser: User) = flow {
        emit(Result.Loading())
        try {
            val receivedMessages = messengerApi.fetchFrequentlyUserMessages(currentUser)
                .toImmutableSet()
            emit(Result.Success(receivedMessages))
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching home posts", e)
            emit(
                Result.Error(
                    message = e.message ?: "Unhandled error",
                    throwable = e,
                )
            )
        }
    }
}
