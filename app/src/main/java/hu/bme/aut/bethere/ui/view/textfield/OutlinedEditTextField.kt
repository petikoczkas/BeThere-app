package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun OutlinedEditTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    placeholder: String = "",
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        enabled = enabled,
        label = { Text(placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(MaterialTheme.beThereDimens.minBeThereTextFieldHeight)
            .clip(RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize))
            .clickable { onClick() },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onBackground,
            leadingIconColor = MaterialTheme.colors.onSecondary,
            unfocusedBorderColor = MaterialTheme.colors.onSecondary,
            focusedBorderColor = MaterialTheme.colors.primary,
            placeholderColor = MaterialTheme.colors.onSecondary,
            disabledTextColor = MaterialTheme.colors.onBackground,
            disabledBorderColor = MaterialTheme.colors.onSecondary,
            disabledLabelColor = MaterialTheme.colors.onSecondary,
        ),
        textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize)
    )
}