package dev.tonholo.composeinstagram.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Lotion = Color(0xFFFAFAFA)
private val CodGray = Color(0xFF1A1A1A)
private val RaisinBlack = Color(0xFF262626)
private val Azure = Color(0xFF0094F4)
private val Coral = Color(0xFFFF3040)
private val Nickel = Color(0xFF737373)
private val DarkGray = Color(0xFFA8A8A8)

interface AppColors {
    val primary: Color
    val onPrimary: Color
    val secondary: Color
    val onSecondary: Color
    val background: Color
    val onBackground: Color
    val onBackgroundVariant: Color
    val surface: Color
    val onSurface: Color
    val surfaceVariant: Color
    val onSurfaceVariant: Color
    val error: Color
    val onError: Color
    val outline: Color
    val onOutline: Color

    fun toMaterial(darkTheme: Boolean): Colors = if (darkTheme) {
        darkColors(
            primary = primary,
            primaryVariant = primary,
            secondary = secondary,
            secondaryVariant = secondary,
            background = background,
            surface = surface,
            error = error,
            onPrimary = onPrimary,
            onSecondary = onSecondary,
            onBackground = onBackground,
            onSurface = onSurface,
            onError = onError,
        )
    } else {
        lightColors(
            primary = primary,
            primaryVariant = primary,
            secondary = secondary,
            secondaryVariant = secondary,
            background = background,
            surface = surface,
            error = error,
            onPrimary = onPrimary,
            onSecondary = onSecondary,
            onBackground = onBackground,
            onSurface = onSurface,
            onError = onError,
        )
    }
}

internal object LightColorsPalette : AppColors {
    override val primary: Color = Azure
    override val onPrimary: Color = Color.White
    override val secondary: Color = Coral
    override val onSecondary: Color = Color.White
    override val background: Color = Color.White
    override val onBackground: Color = RaisinBlack
    override val onBackgroundVariant: Color = Nickel
    override val surface: Color = Color.White
    override val onSurface: Color = RaisinBlack
    override val surfaceVariant: Color = Lotion
    override val onSurfaceVariant: Color = CodGray
    override val error: Color = Coral
    override val onError: Color = Color.White
    override val outline: Color = CodGray
    override val onOutline: Color = Color.White
}

internal object DarkColorsPalette : AppColors {
    override val primary: Color = Azure
    override val onPrimary: Color = Color.White
    override val secondary: Color = Coral
    override val onSecondary: Color = Color.White
    override val background: Color = Color.Black
    override val onBackground: Color = Lotion
    override val onBackgroundVariant: Color = DarkGray
    override val surface: Color = Color.Black
    override val onSurface: Color = Lotion
    override val surfaceVariant: Color = CodGray
    override val onSurfaceVariant: Color = Lotion
    override val error: Color = Coral
    override val onError: Color = Color.White
    override val outline: Color = CodGray
    override val onOutline: Color = Color.White
}

internal val LocalAppColors = staticCompositionLocalOf<AppColors> { LightColorsPalette }
