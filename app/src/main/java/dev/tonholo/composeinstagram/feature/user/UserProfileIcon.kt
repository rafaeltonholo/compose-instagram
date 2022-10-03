package dev.tonholo.composeinstagram.feature.user

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.tonholo.composeinstagram.R
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun UserProfileIcon(
    profileIconUrl: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    ) {

        AsyncImage(
            model = profileIconUrl,
            contentDescription = "Profile icon",
            placeholder = painterResource(id = R.drawable.user_profile_placeholder),
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 0.5.dp,
                    color = Theme.colors.onSurfaceVariant.copy(alpha = 0.1f),
                    shape = CircleShape,
                )
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
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
        UserProfileIcon(
            profileIconUrl = "https://picsum.photos/300/300.jpg?random=1",
            modifier = Modifier.size(50.dp)
        )
    }
}
