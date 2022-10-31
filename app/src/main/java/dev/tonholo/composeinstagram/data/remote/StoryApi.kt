package dev.tonholo.composeinstagram.data.remote

import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserStory

interface StoryApi {
    suspend fun fetchStories(): List<UserStory>

    suspend fun fetchStory(from: User): List<UserStory>
}
