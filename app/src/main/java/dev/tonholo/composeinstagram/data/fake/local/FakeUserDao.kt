package dev.tonholo.composeinstagram.data.fake.local

import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserTag

object FakeUserDao : UserDao {
    override suspend fun getCurrentUser(): User = FakeData.currentUser

    override suspend fun getFollowers(from: User): Set<User> =
        FakeData.userFollows
            .filter { it.follow == from }
            .map { it.user }
            .toSet()

    override suspend fun getFollowings(from: User): Set<User> =
        FakeData.userFollows
            .filter { it.user == from }
            .map { it.follow }
            .toSet()

    override suspend fun getUser(from: UserTag): User? = FakeData.users.firstOrNull { it.userTag == from }
}
