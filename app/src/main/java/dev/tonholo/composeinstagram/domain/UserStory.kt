package dev.tonholo.composeinstagram.domain

data class UserStory(
    val owner: User,
    val stories: List<Story>,
)
