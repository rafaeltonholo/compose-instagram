package dev.tonholo.composeinstagram.feature.home

import dev.tonholo.composeinstagram.feature.home.components.StoryItem
import kotlinx.collections.immutable.ImmutableList

data class StoryState(
    val isLoading: Boolean = false,
    val storyItems: ImmutableList<StoryItem>? = null,
    val errorMessage: String? = null,
)

data class HomeUiState(
    val storyState: StoryState = StoryState(),
)
