package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class BeThereColors(
    val white: Color = Color(0xFFFFFFFF),
    val black: Color = Color(0xFF000000),
    val gray: Color = Color(0xFFD9D9D9),
    val darkGray: Color = Color.Gray,
    val transparent: Color = Color.Transparent,

    val colors: Colors = lightColors()
)

val LocalColors = staticCompositionLocalOf { BeThereColors() }

val MaterialTheme.beThereColors: BeThereColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current