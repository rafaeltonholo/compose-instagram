package dev.tonholo.composeinstagram.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tonholo.composeinstagram.common.Result
import dev.tonholo.composeinstagram.feature.home.usercase.FetchStories
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    fetchStories: FetchStories,
) : ViewModel() {
    val state: StateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
        .combine(fetchStories()) { current, stories ->
            val currentStoryState = current.storyState
            val newStoryState = when (stories) {
                is Result.Error -> currentStoryState.copy(
                    isLoading = false,
                    errorMessage = stories.message,
                )

                is Result.Loading -> currentStoryState.copy(
                    isLoading = true,
                    storyItems = stories.data?.toImmutableList(),
                )

                is Result.Success -> currentStoryState.copy(
                    isLoading = false,
                    storyItems = stories.data.toImmutableList(),
                )
            }
            current.copy(storyState = newStoryState)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeUiState())
}
