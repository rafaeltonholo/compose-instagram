package dev.tonholo.composeinstagram.domain

import java.time.LocalDate

data class Story(
    val mediaUrl: String,
    val postDate: LocalDate,
    val seenAt: LocalDate? = null,
)
