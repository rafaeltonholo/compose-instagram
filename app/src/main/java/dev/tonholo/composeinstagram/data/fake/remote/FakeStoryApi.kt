package dev.tonholo.composeinstagram.data.fake.remote

import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.data.remote.StoryApi
import dev.tonholo.composeinstagram.domain.UserStory

object FakeStoryApi : StoryApi {
    override suspend fun fetchStories(): List<UserStory> = FakeData
        .userStories
        .sortedWith(compareBy {
            if (it.owner == FakeData.currentUser) {
                1
            } else {
                -1
            }
        })
}
