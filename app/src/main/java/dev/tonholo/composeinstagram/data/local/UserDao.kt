package dev.tonholo.composeinstagram.data.local

import dev.tonholo.composeinstagram.domain.User

interface UserDao {
    suspend fun getCurrentUser(): User
}
