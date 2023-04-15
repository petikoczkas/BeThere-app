package hu.bme.aut.bethere.ui.view.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor =
        if (isPressed) MaterialTheme.colors.background else MaterialTheme.colors.primary
    val border = BorderStroke(
        width = if (isPressed) MaterialTheme.beThereDimens.primaryButtonBorderSize
        else MaterialTheme.beThereDimens.gapNone,
        color = MaterialTheme.colors.primaryVariant
    )

    OutlinedButton(
        onClick = onClick,
        interactionSource = interactionSource,
        modifier = modifier.height(MaterialTheme.beThereDimens.minButtonHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = MaterialTheme.colors.primaryVariant,
            disabledContentColor = MaterialTheme.colors.onPrimary

        ),
        enabled = enabled,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.primaryButtonCornerSize),
        border = border
    ) {
        Text(
            text = text,
            style = MaterialTheme.beThereTypography.primaryButtonTextStyle
        )
    }
}