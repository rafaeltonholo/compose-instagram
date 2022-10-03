package dev.tonholo.composeinstagram.feature.user

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun UserStoryIcon(
    profileImageUrl: String,
    hasStory: Boolean,
    isOwner: Boolean,
    modifier: Modifier = Modifier,
    shouldShowAddIcon: Boolean = true,
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (hasStory) {
                        Modifier.border(
                            width = 2.dp,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFDBA48),
                                    Color(0xFFFF2748),
                                    Color(0xFFD300C5),
                                ),
                            ),
                            shape = CircleShape,
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(4.dp)
        ) {
            UserProfileIcon(
                profileIconUrl = profileImageUrl,
                modifier = Modifier.wrapContentSize(),
            )
        }

        if (isOwner && shouldShowAddIcon && !hasStory) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .size(20.dp)
                    .border(
                        width = 1.dp,
                        color = Theme.colors.background,
                        shape = CircleShape,
                    )
                    .background(color = Theme.colors.primary, shape = CircleShape)
                    .padding(4.dp)
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new story",
                    colorFilter = ColorFilter.tint(Theme.colors.onPrimary)
                )
            }
        }
    }
}

private data class UserStoryIconPreviewParameter(
    val hasStory: Boolean,
    val isOwner: Boolean,
    val shouldShowAddIcon: Boolean,
)

private class UserStoryIconPreviewParameterCollection :
    CollectionPreviewParameterProvider<UserStoryIconPreviewParameter>(
        listOf(
            UserStoryIconPreviewParameter(
                hasStory = true,
                isOwner = true,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                isOwner = true,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                isOwner = false,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                isOwner = false,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                isOwner = true,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                isOwner = true,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                isOwner = false,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                isOwner = false,
                shouldShowAddIcon = false,
            ),
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
    @PreviewParameter(UserStoryIconPreviewParameterCollection::class) params: UserStoryIconPreviewParameter,
) {
    val (hasStory, isOwner) = params
    ComposeInstagramTheme {
        UserStoryIcon(
            profileImageUrl = "",
            hasStory = hasStory,
            isOwner = isOwner,
            modifier = Modifier.size(64.dp)
        )
    }
}
