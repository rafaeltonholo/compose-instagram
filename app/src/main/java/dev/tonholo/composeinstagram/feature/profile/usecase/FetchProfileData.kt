package dev.tonholo.composeinstagram.feature.profile.usecase

import android.util.Log
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.fake.remote.FakeStoryApi
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.data.remote.StoryApi
import dev.tonholo.composeinstagram.domain.User
import kotlinx.coroutines.flow.flow

data class ProfileData(
    val followers: Set<User>,
    val following: Set<User>,
    val hasStory: Boolean,
)

private const val TAG = "FetchProfileData"

class FetchProfileData(
    private val userDao: UserDao = FakeUserDao,
    private val storyApi: StoryApi = FakeStoryApi,
) {
    operator fun invoke(user: User) = flow {
        emit(Result.Loading())
        try {
            val followers = userDao.getFollowers(user)
            val following = userDao.getFollowings(user)
            val hasStory = storyApi.fetchStory(user).any()
            emit(Result.Success(ProfileData(followers, following, hasStory)))
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching profile data", e)
            emit(
                Result.Error(
                    message = e.message ?: "Unhandled error",
                    throwable = e,
                )
            )
        }
    }
}
