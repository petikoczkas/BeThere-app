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
fun EditTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: @Composable () -> Unit = {},
    enabled: Boolean = true,
) {
    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.beThereColors.black,
        disabledTextColor = MaterialTheme.beThereColors.black,
        focusedIndicatorColor = MaterialTheme.beThereColors.black,
        unfocusedIndicatorColor = MaterialTheme.beThereColors.black,
        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
        backgroundColor = MaterialTheme.beThereColors.gray,
        cursorColor = MaterialTheme.beThereColors.black
    )
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = placeHolder,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
        textStyle = MaterialTheme.beThereTypography.editTextFieldTextStyle,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize),
        colors = textFieldColors
    )
}