package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.utils.isValidEmail


@Composable
fun BeThereTextField(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    leadingIcon: Int,
    modifier: Modifier = Modifier,
    keyBoardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    isEmail: Boolean = false,
    isPasswordAgain: Boolean = false,
    firstPassword: String = "",
    isPassword: Boolean = false,
    passwordVisible: Boolean = true,
    onPasswordVisibilityChange: () -> Unit = {}
) {

    var isErrorInText by rememberSaveable { mutableStateOf(false) }

    if (isPasswordAgain) {
        isErrorInText = firstPassword != text
    }
    if (isEmail) {
        isErrorInText = !text.isValidEmail()
    }


    val beThereTextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = MaterialTheme.colors.secondary,
        textColor = MaterialTheme.colors.onBackground,
        leadingIconColor = MaterialTheme.colors.onSecondary,
        trailingIconColor = MaterialTheme.colors.onSecondary,
        unfocusedBorderColor = MaterialTheme.colors.onSecondary,
        focusedBorderColor = MaterialTheme.colors.primary,
        placeholderColor = MaterialTheme.colors.onSecondary,
        errorTrailingIconColor = MaterialTheme.colors.onSecondary,
        errorLeadingIconColor = MaterialTheme.colors.error
    )
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        enabled = enabled,
        isError = if (text != "") isErrorInText else false,
        placeholder = { Text(label, style = MaterialTheme.beThereTypography.placeHolderTextStyle) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.beThereDimens.minBeThereTextFieldHeight),
        colors = beThereTextFieldColors,
        textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize),
        trailingIcon = {
            if (isPassword) {
                val image: Painter =
                    if (passwordVisible) {
                        painterResource(id = R.drawable.ic_visibility_off)
                    } else {
                        painterResource(id = R.drawable.ic_visibility)
                    }

                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(painter = image, contentDescription = null)
                }
            }
        }
    )
}