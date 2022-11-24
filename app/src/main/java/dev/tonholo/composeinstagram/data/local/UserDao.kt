package dev.tonholo.composeinstagram.data.local

import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.domain.UserTag

interface UserDao {
    suspend fun getCurrentUser(): User

    suspend fun getFollowers(from: User): Set<User>

    suspend fun getFollowings(from: User): Set<User>

    suspend fun getUser(from: UserTag): User?
}
