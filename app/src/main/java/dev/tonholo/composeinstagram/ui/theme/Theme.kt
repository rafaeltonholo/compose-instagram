package dev.tonholo.composeinstagram.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
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
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     */
    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

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
        LocalAppColors provides colors,
    ) {
        MaterialTheme(
            colorScheme = colors.toMaterial(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
