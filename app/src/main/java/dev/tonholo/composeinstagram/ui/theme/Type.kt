package dev.tonholo.composeinstagram.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import dev.tonholo.composeinstagram.ui.theme.typography.InstagramSans
import dev.tonholo.composeinstagram.ui.theme.typography.TypographyTokens

@Immutable
data class AppTypography(
    val displayLarge: TextStyle = TypographyTokens.DisplayLarge,
    val displayMedium: TextStyle = TypographyTokens.DisplayMedium,
    val displaySmall: TextStyle = TypographyTokens.DisplaySmall,
    val headlineLarge: TextStyle = TypographyTokens.HeadlineLarge,
    val headlineMedium: TextStyle = TypographyTokens.HeadlineMedium,
    val headlineSmall: TextStyle = TypographyTokens.HeadlineSmall,
    val titleLarge: TextStyle = TypographyTokens.TitleLarge,
    val titleMedium: TextStyle = TypographyTokens.TitleMedium,
    val titleSmall: TextStyle = TypographyTokens.TitleSmall,
    val bodyLarge: TextStyle = TypographyTokens.BodyLarge,
    val bodyMedium: TextStyle = TypographyTokens.BodyMedium,
    val bodySmall: TextStyle = TypographyTokens.BodySmall,
    val labelLarge: TextStyle = TypographyTokens.LabelLarge,
    val labelMedium: TextStyle = TypographyTokens.LabelMedium,
    val labelSmall: TextStyle = TypographyTokens.LabelSmall,
) {
    fun toMaterial(): Typography = Typography(
        defaultFontFamily = InstagramSans,
        h1 = displaySmall,
        h2 = headlineLarge,
        h3 = headlineMedium,
        h4 = headlineSmall,
        h5 = titleLarge,
        h6 = labelSmall,
        subtitle1 = titleMedium,
        subtitle2 = titleSmall,
        body1 = bodyLarge,
        body2 = bodyMedium,
        button = labelLarge,
        caption = bodySmall,
        overline = labelMedium,
    )
}

val Typography = AppTypography()
val LocalTypography = staticCompositionLocalOf { Typography }
