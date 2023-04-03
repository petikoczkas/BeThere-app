package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class BeThereColors(
    val black: Color = Color(0xFF000000),
    val gray: Color = Color(0xFFD9D9D9),
    val darkGray: Color = Color.Gray,
    val transparent: Color = Color.Transparent,

    val blue: Color = Color(0xFF3C4D74),
    val lightBlue: Color = Color(0xFFE4E8F1),
    val white: Color = Color(0xFFFFFFFF),

    val darkBlue: Color = Color(0xFF1F2129),
    val darkLightBlue: Color = Color(0xFF7388B0),
    val darkBluishGray: Color = Color(0xFF424554),
    val lightGray: Color = Color(0xFF9EA2AE),

    val green: Color = Color(0xFF0B7F00),
    val red: Color = Color(0xFFCE0328),


    val lightColors: Colors = lightColors(
        primary = blue,
        onPrimary = white,
        secondary = lightBlue,
        onSecondary = darkBlue,
        background = white,
        onBackground = darkBlue,
        error = red,
        onError = white,
    ),

    val darkColors: Colors = darkColors(
        primary = darkLightBlue,
        onPrimary = darkBlue,
        primaryVariant = darkLightBlue.copy(alpha = 0.6F),
        secondary = darkBluishGray,
        onSecondary = lightGray,
        background = darkBlue,
        onBackground = white,
        error = red,
        onError = darkBlue,
    )
)

val LocalColors = staticCompositionLocalOf { BeThereColors() }

val MaterialTheme.beThereColors: BeThereColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current