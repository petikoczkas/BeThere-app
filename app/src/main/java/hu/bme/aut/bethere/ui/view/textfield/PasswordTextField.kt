package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import hu.bme.aut.bethere.R

@Composable
fun PasswordTextField(
    password: String,
    onPasswordTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPasswordReg: Boolean = false,
    label: String = stringResource(id = R.string.password),
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    BeThereTextField(
        text = password,
        onTextChange = onPasswordTextChange,
        label = label,
        leadingIcon = R.drawable.ic_password,
        isPassword = true,
        isPasswordReg = isPasswordReg,
        passwordVisible = passwordVisible,
        onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
        keyBoardType = KeyboardType.Password,
        modifier = modifier
    )
}