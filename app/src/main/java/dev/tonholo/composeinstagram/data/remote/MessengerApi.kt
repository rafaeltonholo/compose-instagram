package dev.tonholo.composeinstagram.data.remote

import dev.tonholo.composeinstagram.domain.Message
import dev.tonholo.composeinstagram.domain.User

interface MessengerApi {
    suspend fun requestReceivedMessages(user: User): List<Message>
}
