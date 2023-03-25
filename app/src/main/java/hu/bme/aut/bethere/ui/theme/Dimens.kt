package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BeThereDimens(
    val minButtonHeight: Dp = 50.dp,
    val dividerThickness: Dp = 1.dp,

    val gapNone: Dp = 0.dp,
    val gapVeryTiny: Dp = 1.dp,
    val gapTiny: Dp = 2.dp,
    val gapSmall: Dp = 4.dp,
    val gapMedium: Dp = 8.dp,
    val gapNormal: Dp = 16.dp,
    val gapLarge: Dp = 24.dp,
    val gapVeryLarge: Dp = 32.dp,
    val gapVeryVeryLarge: Dp = 56.dp,
    val gapExtraLarge: Dp = 64.dp,

    val minBeThereTextFieldHeight: Dp = 56.dp,
    val minSearchFieldHeight: Dp = 40.dp,
    val minUserCardHeight: Dp = 62.dp,
    val minEventCardHeight: Dp = 70.dp,

    val userBoxHeight: Dp = 252.dp,

    val primaryButtonCornerSize: Dp = 20.dp,
    val textFieldCornerSize: Dp = 20.dp,
    val cardCornerSize: Dp = 20.dp,

    val circularProgressIndicatorSize: Dp = 80.dp,

    )

val LocalDimens = staticCompositionLocalOf { BeThereDimens() }

val MaterialTheme.beThereDimens: BeThereDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current