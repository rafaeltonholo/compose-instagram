package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

// TODO: Add more clickable text to show full text from the owner post
@Composable
fun PostCommentSection(
    owner: UserTag,
    ownerComment: String?,
    commentCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ownerComment?.let { ownerComment ->
            Text(
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("${owner.tag} ")
                    pop()
                    append(ownerComment)
                },
                style = Theme.typography.bodySmall,
                color = Theme.colors.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (commentCount > 0) {
            Text(
                text = "See all $commentCount comments",
                style = Theme.typography.bodySmall,
                color = Theme.colors.onBackgroundVariant,
            )
        }
    }
}

private data class PostCommentSectionParams(
    val owner: UserTag,
    val ownerComment: String?,
    val commentCount: Int,
)

private class PostCommentSectionParamsCol : CollectionPreviewParameterProvider<PostCommentSectionParams>(
    listOf(
        PostCommentSectionParams(
            owner = UserTag("rafaeltonholo"),
            ownerComment = null,
            commentCount = 0,
        ),
        PostCommentSectionParams(
            owner = UserTag("rafaeltonholo"),
            ownerComment = "That is a nice post",
            commentCount = 0,
        ),
        PostCommentSectionParams(
            owner = UserTag("rafaeltonholo"),
            ownerComment = "That is a nice post",
            commentCount = 100,
        ),
        PostCommentSectionParams(
            owner = UserTag("rafaeltonholo"),
            ownerComment = "That is a very long comment ".repeat(100),
            commentCount = 100,
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
    @PreviewParameter(PostCommentSectionParamsCol::class) params: PostCommentSectionParams,
) {
    ComposeInstagramTheme {
        val (
            owner,
            ownerCount,
            commentCount,
        ) = params
        PostCommentSection(
            owner,
            ownerCount,
            commentCount,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
