package dev.tonholo.composeinstagram.ui.theme.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import dev.tonholo.composeinstagram.R

val InstagramSans = FontFamily(
    Font(
        resId = R.font.instagramsans_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.instagramsans_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.instagramsans_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.instagramsans_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
)
val InstagramSansCondensed = FontFamily(
    Font(
        resId = R.font.instagramsanscondensed_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.instagramsanscondensed_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
)
val InstagramSansScript = FontFamily(
    Font(
        resId = R.font.instagramsansscript_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.instagramsansscript_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal,
    ),
)
val InstagramSansHeadline = FontFamily(
    Font(
        resId = R.font.instagramsansheadline_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
)

internal object TypefaceTokens {
    val Brand = InstagramSansHeadline
    val Plain = InstagramSans
    val WeightBold = FontWeight.Bold
    val WeightMedium = FontWeight.Medium
    val WeightRegular = FontWeight.Normal
}
