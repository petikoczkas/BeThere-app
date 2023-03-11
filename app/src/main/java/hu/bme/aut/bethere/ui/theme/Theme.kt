package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/*private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)*/

@Composable
fun BeThereTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides BeThereColors(),
        LocalTypography provides BeThereTypography(),
        LocalDimens provides BeThereDimens()
    ) {
        MaterialTheme(
            colors = LocalColors.current.colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}