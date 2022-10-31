package dev.tonholo.composeinstagram.data.fake.remote

import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.data.remote.MessengerApi
import dev.tonholo.composeinstagram.domain.Message
import dev.tonholo.composeinstagram.domain.User

object FakeMessengerApi : MessengerApi {

    override suspend fun requestReceivedMessages(user: User): List<Message> = FakeData
        .messages
        .filter { message -> message.to == user }
        .sortedByDescending { it.date }
        .distinctBy { it.from }
}
