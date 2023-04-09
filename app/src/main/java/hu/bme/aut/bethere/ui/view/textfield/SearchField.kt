package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun SearchField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.beThereDimens.gapNormal,
                top = MaterialTheme.beThereDimens.gapNormal,
                end = MaterialTheme.beThereDimens.gapNormal,
                bottom = MaterialTheme.beThereDimens.gapMedium,
            )
            .height(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onBackground,
            leadingIconColor = MaterialTheme.colors.onSecondary,
            unfocusedBorderColor = MaterialTheme.colors.onSecondary,
            focusedBorderColor = MaterialTheme.colors.primary,
            placeholderColor = MaterialTheme.colors.onSecondary,
        ),
        textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize)
    )
    Divider(
        color = MaterialTheme.colors.primary,
        thickness = MaterialTheme.beThereDimens.dividerThickness,
        modifier = Modifier.padding(horizontal = MaterialTheme.beThereDimens.gapNormal)
    )
}