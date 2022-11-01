package dev.tonholo.composeinstagram.feature.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.data.fake.local.FakeUserDao
import dev.tonholo.composeinstagram.data.local.UserDao
import dev.tonholo.composeinstagram.feature.messenger.components.MessageItemState
import dev.tonholo.composeinstagram.feature.messenger.usecase.FetchFrequentlyUserMessages
import dev.tonholo.composeinstagram.feature.messenger.usecase.RequestMyMessages
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MessengerViewModel(
    private val userDao: UserDao = FakeUserDao,
    private val requestMyMessages: RequestMyMessages = RequestMyMessages(),
    private val fetchFrequentlyUserMessages: FetchFrequentlyUserMessages = FetchFrequentlyUserMessages(),
) : ViewModel() {
    private val _state = MutableStateFlow(MessengerUiState())
    val state = _state.asStateFlow()
    private var searchingJob: Job? = null
    private var allMessages: ImmutableList<MessageItemState>? = null

    init {
        viewModelScope.launch {
            val currentUser = userDao.getCurrentUser()
            _state.update { it.copy(currentUser = currentUser) }

            requestMyMessages(currentUser).collectLatest { state ->
                when (state) {
                    is Result.Error -> _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = state.message,
                        )
                    }

                    is Result.Loading -> _state.update { currentState ->
                        currentState.copy(
                            isLoading = true,
                            errorMessage = null,
                        )
                    }

                    is Result.Success -> _state.update { currentState ->
                        allMessages = state.data
                        currentState.copy(
                            isLoading = false,
                            errorMessage = null,
                            messages = state.data,
                        )
                    }
                }
            }

            fetchFrequentlyUserMessages(currentUser).collectLatest { state ->
                when (state) {
                    is Result.Error -> _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = state.message,
                        )
                    }

                    is Result.Loading -> _state.update { currentState ->
                        currentState.copy(
                            isLoading = true,
                            errorMessage = null,
                        )
                    }

                    is Result.Success -> _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            errorMessage = null,
                            frequentlyUserMessages = state.data,
                        )
                    }
                }
            }
        }
    }

    fun onSearchChange(searchTerm: String) {
        searchingJob?.cancel()
        searchingJob = viewModelScope.launch {
            _state.update { currentState ->
                val messages = if (searchTerm.isNotBlank()) {
                    allMessages
                        ?.filter { it.senderName.contains(searchTerm, ignoreCase = true) }
                        ?.toImmutableList()
                } else {
                    allMessages
                }

                currentState.copy(
                    searchTerm = searchTerm,
                    messages = messages,
                )
            }
        }
    }
}
