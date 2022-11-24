package dev.tonholo.composeinstagram.feature.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.data.fake.FakeData
import dev.tonholo.composeinstagram.feature.profile.ProfileUiState
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun ProfileActionsSection(
    state: ProfileUiState,
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onFollowClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onSuggestFollowsClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val actionModifier = Modifier.weight(1f)
        val isPrivate = if (state is ProfileUiState.OtherProfile) {
            OthersProfileActions(
                isFollowing = state.isFollowing,
                isPrivate = state.isPrivate,
                onFollowClick = onFollowClick,
                onMessageClick = onMessageClick,
                modifier = actionModifier
            )
            state.isPrivate
        } else {
            MyProfileActions(
                modifier = actionModifier,
                onEditProfileClick = onEditProfileClick,
            )
            false
        }

        if (!isPrivate) {
            ActionButton(
                onClick = onSuggestFollowsClick,
                modifier = Modifier.width(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonAdd,
                    contentDescription = "Follow suggestions",
                    tint = Theme.colors.onBackground,
                )
            }
        }
    }
}

@Composable
private fun MyProfileActions(
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit,
) {
    ActionButton(
        onClick = onEditProfileClick,
        modifier = modifier,
    ) {
        Text(
            text = "Edit profile",
            style = Theme.typography.bodyMedium,
            color = Theme.colors.onBackground,
        )
    }
}

@Composable
private fun OthersProfileActions(
    isFollowing: Boolean,
    isPrivate: Boolean,
    onFollowClick: () -> Unit,
    onMessageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ActionButton(
        onClick = onFollowClick,
        modifier = modifier,
        backgroundColor = if (isFollowing) null else Theme.colors.primary,
    ) {
        Text(
            text = if (isFollowing) "Following" else "Follow",
            style = Theme.typography.bodyMedium,
            color = if (isFollowing) Theme.colors.onBackground else Theme.colors.onPrimary,
        )
        if (isFollowing) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = null,
                tint = Theme.colors.onBackground,
            )
        }
    }
    if (!isPrivate || isFollowing) {
        ActionButton(
            onClick = onMessageClick,
            modifier = modifier,
        ) {
            Text(
                text = "Message",
                style = Theme.typography.bodyMedium,
                color = Theme.colors.onBackground,
            )
        }
    }
}

@Composable
private fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor ?: Theme.colors.onBackground.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.elevation(0.dp),
        modifier = modifier,
    ) {
        content()
    }
}

private class ProfileActionsSectionParamCol : CollectionPreviewParameterProvider<ProfileUiState>(
    listOf(
        ProfileUiState.MyProfile(
            user = FakeData.currentUser,
            bio = "",
            hasStory = false,
            posts = emptyList(),
            followersCount = 0,
            followingCount = 0,
        ),
        ProfileUiState.OtherProfile(
            user = FakeData.currentUser,
            bio = "",
            hasStory = false,
            posts = emptyList(),
            followersCount = 0,
            followingCount = 0,
            isFollowing = false,
            isPrivate = false,
        ),
        ProfileUiState.OtherProfile(
            user = FakeData.currentUser,
            bio = "",
            hasStory = false,
            posts = emptyList(),
            followersCount = 0,
            followingCount = 0,
            isFollowing = true,
            isPrivate = false
        ),
        ProfileUiState.OtherProfile(
            user = FakeData.currentUser,
            bio = "",
            hasStory = false,
            posts = emptyList(),
            followersCount = 0,
            followingCount = 0,
            isFollowing = false,
            isPrivate = true
        ),
        ProfileUiState.OtherProfile(
            user = FakeData.currentUser,
            bio = "",
            hasStory = false,
            posts = emptyList(),
            followersCount = 0,
            followingCount = 0,
            isFollowing = true,
            isPrivate = true
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
    @PreviewParameter(ProfileActionsSectionParamCol::class) state: ProfileUiState,
) {
    ComposeInstagramTheme {
        ProfileActionsSection(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}
