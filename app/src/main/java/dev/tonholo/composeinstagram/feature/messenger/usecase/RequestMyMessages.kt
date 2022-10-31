package dev.tonholo.composeinstagram.feature.messenger.usecase

import android.util.Log
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.remote.FakeMessengerApi
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.data.remote.MessengerApi
import dev.tonholo.composeinstagram.data.remote.StoryApi
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.extension.toTimestamp
import dev.tonholo.composeinstagram.feature.messenger.components.MessageItemState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.flow

private const val TAG = "RequestMyMessages"

class RequestMyMessages(
    private val messengerApi: MessengerApi = FakeMessengerApi,
    private val storyApi: StoryApi = FakeStoryApi,
) {
    operator fun invoke(currentUser: User) = flow {
        emit(Result.Loading())

        try {
            val receivedMessages = messengerApi.requestReceivedMessages(currentUser)
                .map { message ->
                    with(message) {
                        MessageItemState(
                            senderUserTag = from.userTag,
                            senderProfileImageUrl = from.profileImage,
                            senderName = from.name,
                            senderHasStory = storyApi.fetchStory(from).any(),
                            messageContent = content,
                            sentAt = message.date.toTimestamp(
                                yearsLabel = "y",
                                weeksLabel = "w",
                                daysLabel = "d",
                                hoursLabel = "h",
                                minutesLabel = "m",
                            ),
                            isNotRead = !message.hasRead,
                        )
                    }
                }
                .sortedBy { !it.isNotRead }
                .toImmutableList()
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
