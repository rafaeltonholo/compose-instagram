package dev.tonholo.composeinstagram.feature.messenger

import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.messenger.components.MessageItemState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

data class MessengerUiState(
    val currentUser: User? = null,
    val searchTerm: String? = null,
    val frequentlyUserMessages: ImmutableSet<User>? = null,
    val messages: ImmutableList<MessageItemState>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
