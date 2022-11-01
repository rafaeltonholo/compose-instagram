package dev.tonholo.composeinstagram.data.remote

import dev.tonholo.composeinstagram.domain.Message
import dev.tonholo.composeinstagram.domain.User

interface MessengerApi {
    suspend fun requestReceivedMessages(user: User): List<Message>

    suspend fun fetchFrequentlyUserMessages(user: User): List<User>

    suspend fun getNonReadMessageCount(user: User): Int
}
