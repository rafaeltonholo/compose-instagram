package dev.tonholo.composeinstagram.feature.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.feature.user.UserStoryIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class StoryItem(
    val author: String,
    val isOwner: Boolean,
    val profileImage: String,
    val hasPendingStories: Boolean,
)

@Composable
fun StoryRow(
    modifier: Modifier = Modifier,
    storyItems: ImmutableList<StoryItem>,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        itemsIndexed(storyItems) { index, story ->
            if (index == 0) Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.width(72.dp)) {
                UserStoryIcon(
                    profileImageUrl = story.profileImage,
                    hasStory = story.hasPendingStories,
                    isOwner = story.isOwner,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = if (story.isOwner) "Your story" else story.author,
                    style = Theme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Theme.colors.onBackground,
                )
            }
        }
    }
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun Preview() {
    ComposeInstagramTheme {
        StoryRow(
            modifier = Modifier.fillMaxWidth(),
            storyItems = FakeData.userStories
                .mapIndexed { index, it ->
                    StoryItem(
                        author = it.owner.userTag.tag,
                        isOwner = index == 0,
                        profileImage = it.owner.profileImage,
                        hasPendingStories = it.stories.isNotEmpty(),
                    )
                }
                .toImmutableList()
        )
    }
}
