package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun BeThereTextField(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    leadingIcon: Int,
    modifier: Modifier = Modifier,
    keyBoardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: () -> Unit = {}
) {

    val beThereTextFieldColors = TextFieldDefaults.textFieldColors(
        focusedLabelColor = MaterialTheme.beThereColors.darkGray,
        textColor = MaterialTheme.beThereColors.black,
        focusedIndicatorColor = MaterialTheme.beThereColors.transparent,
        unfocusedIndicatorColor = MaterialTheme.beThereColors.transparent,
        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
        backgroundColor = MaterialTheme.beThereColors.gray,
        cursorColor = MaterialTheme.beThereColors.black
    )
    if (!isPassword) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            enabled = enabled,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
            colors = beThereTextFieldColors,
            textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
            shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize),
        )
    } else {
        TextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            label = { Text(label) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
            colors = beThereTextFieldColors,
            textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
            shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize),
            trailingIcon = {
                val image: Painter =
                    if (passwordVisible) {
                        painterResource(id = R.drawable.ic_visibility)
                    } else {
                        painterResource(id = R.drawable.ic_visibility_off)
                    }

                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(painter = image, contentDescription = null)
                }
            }
        )
    }
}