package dev.tonholo.composeinstagram.feature.home.usercase

import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.local.UserDao
import kotlinx.coroutines.flow.flow

class FetchUserData(
    private val userDao: UserDao,
) {
    operator fun invoke() = flow {
        emit(Result.Loading())
        try {
            val user = userDao.getCurrentUser()
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Unhandled error", throwable = e))
        }
    }
}
