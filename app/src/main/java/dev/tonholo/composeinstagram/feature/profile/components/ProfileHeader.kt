package dev.tonholo.composeinstagram.feature.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.extension.toInternetString
import dev.tonholo.composeinstagram.feature.user.UserStoryIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

private const val BIO_SEE_MORE_LIMIT = 150

@Composable
fun ProfileHeader(
    userName: String,
    userBio: String?,
    profileImageUrl: String,
    hasStory: Boolean,
    postsCount: Int,
    followersCount: Int,
    followingCount: Int,
    modifier: Modifier = Modifier,
    onUserProfileClick: () -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserStoryIcon(
                profileImageUrl = profileImageUrl,
                hasStory = hasStory,
                modifier = Modifier.size(80.dp),
                onClick = onUserProfileClick,
                shouldShowAddIcon = false,
            )
            ProfileInfoColumn(
                count = postsCount,
                label = "Posts",
                modifier = Modifier.weight(0.3f)
            )
            ProfileInfoColumn(
                count = followersCount,
                label = "Followers",
                modifier = Modifier.weight(0.3f)
            )
            ProfileInfoColumn(
                count = followingCount,
                label = "Following",
                modifier = Modifier.weight(0.3f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = userName,
            style = Theme.typography.titleMedium,
            color = Theme.colors.onBackground,
        )
        userBio?.let { bio ->
            var isBioExpanded by remember { mutableStateOf(false) }
            val bioText by remember {
                derivedStateOf {
                    if (isBioExpanded || bio.length < BIO_SEE_MORE_LIMIT) {
                        bio
                    } else {
                        bio.substring(0, BIO_SEE_MORE_LIMIT) + "..."
                    }
                }
            }
            ClickableText(
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(color = Theme.colors.onBackground))
                    append(bioText)
                    if (!isBioExpanded && bio.length > BIO_SEE_MORE_LIMIT) {
                        pushStyle(SpanStyle(color = Theme.colors.onBackground.copy(alpha = 0.5f)))
                        append(" See more")
                    }
                },
                style = Theme.typography.bodyMedium,
                onClick = {
                    if (!isBioExpanded) isBioExpanded = true
                }
            )
        }
    }
}

@Composable
private fun ProfileInfoColumn(
    count: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = count.toInternetString(),
            style = Theme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Theme.colors.onBackground,
        )
        Text(
            text = label,
            style = Theme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            color = Theme.colors.onBackground,
        )
    }
}

private data class ProfileHeaderParams(
    val hasStory: Boolean,
    val postsCount: Int,
    val flowersCount: Int,
    val flowingCount: Int,
    val userBio: String?,
)

private class ProfileHeaderParamsCol : CollectionPreviewParameterProvider<ProfileHeaderParams>(
    listOf(
        ProfileHeaderParams(
            hasStory = false,
            postsCount = 0,
            flowersCount = 0,
            flowingCount = 0,
            userBio = null,
        ),
        ProfileHeaderParams(
            hasStory = true,
            postsCount = 1,
            flowersCount = 10,
            flowingCount = 100,
            userBio = "Some text",
        ),
        ProfileHeaderParams(
            hasStory = true,
            postsCount = 1_000,
            flowersCount = 10_500,
            flowingCount = 999_999,
            userBio = "A long, " + "long, ".repeat(100) + "text"
        ),
        ProfileHeaderParams(
            hasStory = true,
            postsCount = 1_000_000,
            flowersCount = 10_000_000,
            flowingCount = 1_000_000_000,
            userBio = "😅🛫🛫",
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
    @PreviewParameter(ProfileHeaderParamsCol::class) params: ProfileHeaderParams,
) {
    ComposeInstagramTheme {
        ProfileHeader(
            userName = "Rafael Tonholo",
            userBio = params.userBio,
            profileImageUrl = "",
            hasStory = params.hasStory,
            postsCount = params.postsCount,
            followersCount = params.flowersCount,
            followingCount = params.flowingCount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}
