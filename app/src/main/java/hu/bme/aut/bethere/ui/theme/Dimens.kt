package hu.bme.aut.bethere.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BeThereDimens(
    val minButtonHeight: Dp = 52.dp,
    val minBeThereTextFieldHeight: Dp = 60.dp,

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

    val minSearchFieldHeight: Dp = 40.dp,
    val minUserCardHeight: Dp = 64.dp,
    val userCardImageSize: Dp = 36.dp,
    val minEventCardHeight: Dp = 64.dp,

    val textFieldFocusedBorderThickness: Dp = 3.dp,
    val textFieldUnfocusedBorderThickness: Dp = 2.dp,
    val textFieldCornerSize: Dp = 12.dp,

    val userBoxHeight: Dp = 252.dp,
    val settingsImageSize: Dp = 200.dp,

    val primaryButtonCornerSize: Dp = 12.dp,
    val primaryButtonBorderSize: Dp = 2.dp,

    val cardCornerSize: Dp = 12.dp,
    val messageCardSize: Dp = 340.dp,
    val messageCardCornerSize: Dp = 16.dp,
    val messageCardImageSize: Dp = 20.dp,

    val circularProgressIndicatorSize: Dp = 80.dp,

    )

val LocalDimens = staticCompositionLocalOf { BeThereDimens() }

val MaterialTheme.beThereDimens: BeThereDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current