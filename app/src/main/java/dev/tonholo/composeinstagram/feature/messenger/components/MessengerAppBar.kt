package dev.tonholo.composeinstagram.feature.messenger.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun MessengerAppBar(
    userTag: UserTag,
    modifier: Modifier = Modifier,
    onNavigateBackClick: () -> Unit = {},
    onUserNameClick: () -> Unit = {},
    onVideoCallClick: () -> Unit = {},
    onNewMessageClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = Theme.colors.background,
        title = {
            Row(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onUserNameClick,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = userTag.tag,
                    style = Theme.typography.titleMedium,
                    color = Theme.colors.onBackground,
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Change user",
                    tint = Theme.colors.onBackground,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Theme.colors.onBackground,
                )
            }
        },
        actions = {
            IconButton(onClick = onVideoCallClick) {
                Icon(
                    imageVector = Icons.Default.VideoCall,
                    contentDescription = "Start video call",
                    tint = Theme.colors.onBackground,
                )
            }
            IconButton(onClick = onNewMessageClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New message",
                    tint = Theme.colors.onBackground,
                )
            }
        },
        elevation = 0.dp,
    )
}

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun Preview() {
    ComposeInstagramTheme {
        MessengerAppBar(
            userTag = UserTag("rafaeltonholo"),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
