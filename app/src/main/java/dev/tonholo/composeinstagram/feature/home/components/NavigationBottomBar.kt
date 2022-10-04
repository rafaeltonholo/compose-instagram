package dev.tonholo.composeinstagram.feature.post.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.feature.user.UserProfileIcon
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun NavigationBottomBar(
    profileIconUrl: String,
    onSearchClick: () -> Unit,
    onReelsClick: () -> Unit,
    onShopClick: () -> Unit,
    onUserProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(
        modifier = modifier,
        backgroundColor = Theme.colors.background,
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home screen",
                tint = Theme.colors.onBackground,
            )
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search screen",
                    tint = Theme.colors.onBackground,
                )
            }
            IconButton(onClick = onReelsClick) {
                Icon(
                    imageVector = Icons.Outlined.Subscriptions,
                    contentDescription = "Reels screen",
                    tint = Theme.colors.onBackground,
                )
            }
            IconButton(onClick = onShopClick) {
                Icon(
                    imageVector = Icons.Outlined.Shop,
                    contentDescription = "Reels screen",
                    tint = Theme.colors.onBackground,
                )
            }
            UserProfileIcon(
                profileIconUrl = profileIconUrl,
                modifier = Modifier.size(24.dp),
                onClick = onUserProfileClick,
            )
        }
    }
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
        NavigationBottomBar(
            profileIconUrl = "mock",
            onSearchClick = { },
            onReelsClick = { },
            onShopClick = { },
            onUserProfileClick = { },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
