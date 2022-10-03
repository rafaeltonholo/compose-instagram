package dev.tonholo.composeinstagram.data.remote

import dev.tonholo.composeinstagram.domain.UserStory

interface StoryApi {
    suspend fun fetchStories(): List<UserStory>
}
