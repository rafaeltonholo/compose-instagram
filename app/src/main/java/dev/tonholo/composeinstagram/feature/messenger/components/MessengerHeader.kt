package dev.tonholo.composeinstagram.feature.messenger.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.domain.User
import dev.tonholo.composeinstagram.feature.user.UserStoryIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun MessengerHeader(
    searchTerm: String?,
    modifier: Modifier = Modifier,
    frequentlyUserMessages: ImmutableSet<User> = persistentSetOf(),
    onSearchChange: (String) -> Unit = {},
    onRecentUserMessageClick: (User) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {

        BasicTextField(
            value = searchTerm.orEmpty(),
            onValueChange = onSearchChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            textStyle = Theme.typography.bodyMedium.copy(color = Theme.colors.onBackground),
        ) { innerTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = Theme.colors.onBackground.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(30),
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(16.dp),
                    tint = Theme.colors.onBackground.copy(alpha = 0.7f),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box {
                    innerTextField()
                    if (searchTerm.isNullOrBlank()) {
                        Text(
                            text = "Search",
                            style = Theme.typography.bodyMedium,
                            color = Theme.colors.onBackground.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }
        if (frequentlyUserMessages.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(frequentlyUserMessages.size) { index ->
                    val user = frequentlyUserMessages.elementAt(index)
                    Column(modifier = Modifier.widthIn(max = 72.dp)) {
                        UserStoryIcon(
                            profileImageUrl = user.profileImage,
                            hasStory = false,
                            shouldShowAddIcon = false,
                            modifier = Modifier
                                .size(64.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = {
                                onRecentUserMessageClick(user)
                            }
                        )
                        Text(
                            text = user.name,
                            style = Theme.typography.labelSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Theme.colors.onBackground,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                }
            }
        }
    }
}

private class MessengerHeaderParamsCol : CollectionPreviewParameterProvider<ImmutableSet<User>>(
    listOf(
        persistentSetOf(),
        FakeData.users.take(10).toImmutableSet(),
    )
)

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
private fun Preview(
    @PreviewParameter(MessengerHeaderParamsCol::class) recent: ImmutableSet<User>,
) {
    ComposeInstagramTheme {
        MessengerHeader(
            searchTerm = null,
            modifier = Modifier
                .fillMaxWidth(),
            frequentlyUserMessages = recent,
        )
    }
}
