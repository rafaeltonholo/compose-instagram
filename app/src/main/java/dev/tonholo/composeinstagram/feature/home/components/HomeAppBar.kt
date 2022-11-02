package dev.tonholo.composeinstagram.feature.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import dev.tonholo.composeinstagram.ui.theme.ComposeInstagramTheme
import dev.tonholo.composeinstagram.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    messagesCount: Int,
    modifier: Modifier = Modifier,
    onMessengerIconClick: () -> Unit = {},
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
        title = {
            Text(
                text = "Instagram",
                style = Theme.typography.headlineSmall,
                color = Theme.colors.onBackground,
            )
        },
        actions = {
            ActionIcon(imageVector = Icons.Outlined.AddBox)
            ActionIcon(imageVector = Icons.Outlined.FavoriteBorder)
            ActionIcon(
                imageVector = Icons.Outlined.Message,
                contentDescription = "Open messenger",
                notificationCount = messagesCount,
                onClick = onMessengerIconClick,
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Theme.colors.background,
            titleContentColor = Theme.colors.onBackground,
        )
    )
}

@Composable
private fun ActionIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    notificationCount: Int = 0,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick) {
        Box {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = Theme.colors.onBackground,
            )
            if (notificationCount > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(
                            x = when {
                                notificationCount < 10 -> 1.dp
                                notificationCount < 100 -> 4.dp
                                else -> 8.dp
                            },
                            y = (-4).dp,
                        )
                ) {
                    val backgroundColor = Theme.colors.secondary
                    Text(
                        text = if (notificationCount < 100) notificationCount.toString() else "99+",
                        modifier = Modifier
                            .background(color = Theme.colors.secondary, shape = CircleShape)
                            .drawBehind {
                                drawCircle(
                                    color = backgroundColor,
                                    radius = size.maxDimension / 2
                                )
                            }
                            .padding(1.dp),
                        textAlign = TextAlign.Center,
                        style = Theme.typography.labelSmall,
                        color = Theme.colors.onSecondary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

private class HomeAppBarPrevParam : CollectionPreviewParameterProvider<Int>(
    listOf(
        0,
        1,
        10,
        100,
        1000,
    )
)

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
    @PreviewParameter(HomeAppBarPrevParam::class) messagesCount: Int,
) {
    ComposeInstagramTheme {
        HomeAppBar(
            messagesCount = messagesCount,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
