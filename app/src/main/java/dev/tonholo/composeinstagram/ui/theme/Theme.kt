package dev.tonholo.composeinstagram.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Contains functions to access the current theme values provided at the call site's position in the hierarchy.
 */
object Theme {
    /**
     * Retrieves the current [AppColors] at the call site's position in the hierarchy.
     */
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    /**
     * Retrieves the current [AppTypography] at the call site's position in the hierarchy.
     */
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    /**
     * Retrieves the current [Shapes] at the call site's position in the hierarchy.
     */
    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.shapes
}


@Composable
fun ComposeInstagramTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorsPalette
    } else {
        LightColorsPalette
    }

    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalAppColors provides colors,
    ) {
        MaterialTheme(
            colors = colors.toMaterial(darkTheme),
            typography = Typography.toMaterial(),
            shapes = Shapes,
            content = content
        )
    }
}
