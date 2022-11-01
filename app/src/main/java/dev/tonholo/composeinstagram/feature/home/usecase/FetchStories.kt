package dev.tonholo.composeinstagram.feature.home.usecase

import dev.tonholo.composeinstagram.data.remote.StoryApi
import dev.tonholo.composeinstagram.feature.home.components.StoryItem
import kotlinx.coroutines.flow.flow
import dev.tonholo.composeinstagram.common.Result
import java.lang.Exception

class FetchStories(
    private val storyApi: StoryApi,
) {
    operator fun invoke() = flow {
        emit(Result.Loading())

        try {
            val storyItems = storyApi.fetchStories()
                .mapIndexed { index, userStory ->
                    StoryItem(
                        author = userStory.owner.userTag.tag,
                        isOwner = index == 0,
                        profileImage = userStory.owner.profileImage,
                        hasPendingStories = userStory.stories.isNotEmpty(),
                    )
                }
            emit(Result.Success(storyItems))
        } catch (e: Exception) {
            emit(
                Result.Error(
                    message = e.message ?: "Unhandled error",
                    throwable = e,
                )
            )
        }
    }
}
