package dev.tonholo.composeinstagram.feature.user

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun UserStoryIcon(
    profileImageUrl: String,
    hasStory: Boolean,
    modifier: Modifier = Modifier,
    shouldShowAddIcon: Boolean = true,
    storyBorderStrokeWidth: Dp = 2.dp,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (hasStory) {
                        Modifier.border(
                            width = storyBorderStrokeWidth,
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
                .padding(storyBorderStrokeWidth * 2)
        ) {
            UserProfileIcon(
                profileIconUrl = profileImageUrl,
                modifier = Modifier.wrapContentSize(),
            )
        }

        if (shouldShowAddIcon && !hasStory) {
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
    val shouldShowAddIcon: Boolean,
)

private class UserStoryIconPreviewParameterCollection :
    CollectionPreviewParameterProvider<UserStoryIconPreviewParameter>(
        listOf(
            UserStoryIconPreviewParameter(
                hasStory = true,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                shouldShowAddIcon = true,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = true,
                shouldShowAddIcon = false,
            ),
            UserStoryIconPreviewParameter(
                hasStory = false,
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
    val (hasStory, shouldShowAddIcon) = params
    ComposeInstagramTheme {
        UserStoryIcon(
            profileImageUrl = "",
            hasStory = hasStory,
            shouldShowAddIcon = shouldShowAddIcon,
            modifier = Modifier.size(64.dp)
        )
    }
}
