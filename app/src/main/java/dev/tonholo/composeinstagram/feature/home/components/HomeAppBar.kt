package dev.tonholo.composeinstagram.feature.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
) {
    val outlineColor = Theme.colors.outline
    val density = LocalDensity.current
    TopAppBar(
        modifier = modifier
            .drawWithContent {
                drawContent()
                val strokeWidth = with(density) { 1.dp.toPx() }
                val y = size.height - strokeWidth
                drawLine(
                    color = outlineColor,
                    start = Offset(x = 0f, y),
                    end = Offset(x = size.width, y),
                    strokeWidth = strokeWidth,
                )
            },
        backgroundColor = Theme.colors.background,
        title = {
            Text(
                text = "Instagram",
                style = Theme.typography.headlineSmall,
            )
        },
        actions = {
            ActionIcon(imageVector = Icons.Outlined.AddBox)
            ActionIcon(imageVector = Icons.Outlined.FavoriteBorder)
            ActionIcon(imageVector = Icons.Outlined.Message)
        },
        elevation = 0.dp,
    )
}

@Composable
private fun ActionIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Theme.colors.onBackground,
        )
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
        HomeAppBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
