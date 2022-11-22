package dev.tonholo.composeinstagram.feature.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.domain.UserTag
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAppBar(
    userTag: UserTag,
    isPrivateAccount: Boolean,
    modifier: Modifier = Modifier,
    onUserNameClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onUserNameClick,
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isPrivateAccount) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Private account",
                        tint = Theme.colors.onBackground,
                        modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
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
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Outlined.AddBox,
                    contentDescription = "Add a new post",
                    tint = Theme.colors.onBackground,
                )
            }
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Profile Settings",
                    tint = Theme.colors.onBackground,
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Theme.colors.background,
        )
    )
}

private class ProfileAppBarParamCol : CollectionPreviewParameterProvider<Boolean>(listOf(false, true))

@Preview(
    name = "Light mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun Preview(
    @PreviewParameter(ProfileAppBarParamCol::class) isPrivateAccount: Boolean,
) {
    ComposeInstagramTheme {
        ProfileAppBar(
            userTag = UserTag("rafaeltonholo"),
            isPrivateAccount = isPrivateAccount,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
