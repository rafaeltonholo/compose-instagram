package dev.tonholo.composeinstagram.data.local

import dev.tonholo.composeinstagram.data.fake.remote.FakeMessengerApi
import dev.tonholo.composeinstagram.data.remote.MessengerApi
import dev.tonholo.composeinstagram.domain.User

class NotificationService(
    private val messengerApi: MessengerApi = FakeMessengerApi,
) {

    suspend fun getNonReadMessageCount(user: User) = messengerApi.getNonReadMessageCount(user)
}
