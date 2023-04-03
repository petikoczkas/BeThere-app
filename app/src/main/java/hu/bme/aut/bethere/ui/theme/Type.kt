package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import hu.bme.aut.bethere.R

// Set of Material typography styles to start with
val ProximaNova = FontFamily(
    Font(R.font.proximanova_medium, FontWeight.Normal),
    Font(R.font.proximanova_bold, FontWeight.SemiBold)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)

data class BeThereTypography(
    val titleTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 56.sp,
        textAlign = TextAlign.Center
    ),
    val eventTitleTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        textAlign = TextAlign.Center
    ),
    val beThereTextFieldTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    val editTextFieldTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    val cardTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    val primaryButtonTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    val registrateButtonTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        textDecoration = TextDecoration.Underline
    ),
    val descriptionTextStyle: TextStyle = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
)

val LocalTypography = staticCompositionLocalOf { BeThereTypography() }

val MaterialTheme.beThereTypography: BeThereTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current