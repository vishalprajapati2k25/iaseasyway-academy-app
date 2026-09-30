package com.iaseasyway.academy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DuolingoGreen,
    primaryVariant = DuolingoGreenDark,
    secondary = DuolingoGold,
    background = Navy900,
    surface = Navy800,
    onPrimary = PureWhite,
    onSecondary = Navy900,
    onBackground = PureWhite,
    onSurface = PureWhite,
    error = DuolingoRed
)

private val LightColorPalette = lightColors(
    primary = Navy700,
    primaryVariant = Navy900,
    secondary = DuolingoGreen,
    background = Slate100,
    surface = PureWhite,
    onPrimary = PureWhite,
    onSecondary = PureWhite,
    onBackground = TextDark,
    onSurface = TextDark,
    error = DuolingoRed
)

@Composable
fun IEWAcademyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        content = content
    )
}
