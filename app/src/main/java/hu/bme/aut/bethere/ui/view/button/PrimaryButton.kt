package hu.bme.aut.bethere.ui.view.button

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = MaterialTheme.beThereDimens.minButtonHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.beThereColors.gray,
            disabledContentColor = MaterialTheme.beThereColors.darkGray
        ),
        enabled = enabled,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.primaryButtonCornerSize),
    ) {
        Text(
            text = text,
            style = MaterialTheme.beThereTypography.primaryButtonTextStyle
        )
    }
}