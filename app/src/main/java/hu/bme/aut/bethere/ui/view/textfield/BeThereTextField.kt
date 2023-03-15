package hu.bme.aut.bethere.ui.view.textfield

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import java.util.regex.Pattern

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
    isPassword: Boolean = false,
    passwordVisible: Boolean = true,
    onPasswordVisibilityChange: () -> Unit = {}
) {

    var isErrorInText by rememberSaveable { mutableStateOf(false) }

    val beThereTextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.beThereColors.black,
        disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
        backgroundColor = MaterialTheme.beThereColors.gray,
        cursorColor = MaterialTheme.beThereColors.black
    )

    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChange(it)
            if (isPassword) {
                isErrorInText =
                    !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$").matcher(it)
                        .matches()
            }
            if (isEmail) {
                isErrorInText = !Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
        },
        singleLine = true,
        enabled = enabled,
        isError = isErrorInText,
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize),
        trailingIcon = {
            if (isPassword) {
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
        }
    )

    /*TextField(
        value = text,
        onValueChange = {
            onTextChange(it)
            isErrorInText = if (isPassword) {
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$").matcher(it)
                    .matches()
            } else {
                !Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
        },
        singleLine = true,
        enabled = enabled,
        isError = isErrorInText,
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
        trailingIcon = {
            if (isPassword) {
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
        }
    )*/
}