package dev.tonholo.composeinstagram.data.fake.local

import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.domain.User

object FakeUserDao : UserDao {
    override suspend fun getCurrentUser(): User = FakeData.currentUser
}
