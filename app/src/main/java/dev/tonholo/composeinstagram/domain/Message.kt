package dev.tonholo.composeinstagram.domain

import java.time.LocalDateTime

data class Message(
    val from: User,
    val to: User,
    val content: String,
    val date: LocalDateTime,
    val hasRead: Boolean,
)
