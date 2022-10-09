package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.R
import dev.tonholo.composeinstagram.domain.PostLike
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.feature.user.UserProfileIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

private const val USER_PROFILE_SIZE = 24

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PostLikesBar(
    likes: ImmutableSet<PostLike>,
    modifier: Modifier = Modifier,
) {
    val firstAndSecondComments = remember { likes.take(2) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            UserProfileIcon(
                profileIconUrl = firstAndSecondComments.last().profileImageUrl,
                modifier = Modifier
                    .size(USER_PROFILE_SIZE.dp)
                    .offset(x = USER_PROFILE_SIZE.dp / 1.5f)
                    .border(width = 1.dp, color = Theme.colors.background, shape = CircleShape)
            )
            UserProfileIcon(
                profileIconUrl = firstAndSecondComments.first().profileImageUrl,
                modifier = Modifier
                    .size(USER_PROFILE_SIZE.dp)
                    .border(width = 1.dp, color = Theme.colors.background, shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(USER_PROFILE_SIZE.dp))
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.liked_by_text))
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(firstAndSecondComments.first().userTag.tag)
                pop()
                append(stringResource(id = R.string.and_conjunction))
                pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                append(pluralStringResource(
                    id = R.plurals.liked_by_count_text,
                    count = likes.size - 1,
                    likes.size - 1
                ))
            },
            style = Theme.typography.bodySmall,
            color = Theme.colors.onBackground,
        )
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
        PostLikesBar(
            likes = persistentSetOf(
                PostLike(
                    userTag = UserTag("rafaeltonholo"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("rtonholo"),
                    profileImageUrl = "",
                ),
                PostLike(
                    userTag = UserTag("abcd"),
                    profileImageUrl = "",
                ),
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
