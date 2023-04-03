package hu.bme.aut.bethere.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BeThereTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides BeThereColors(),
        LocalTypography provides BeThereTypography(),
        LocalDimens provides BeThereDimens()
    ) {
        MaterialTheme(
            colors = if (isDarkTheme) LocalColors.current.darkColors else LocalColors.current.lightColors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}