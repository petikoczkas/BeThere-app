package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun DisabledTextField(
    text: String,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text,
        onValueChange = {},
        enabled = false,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
            disabledTextColor = MaterialTheme.beThereColors.black,
        ),
        textStyle = MaterialTheme.beThereTypography.disabledTextFieldTextStyle,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize)
    )
}