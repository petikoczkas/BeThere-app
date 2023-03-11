package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import hu.bme.aut.bethere.R


@Composable
fun EmailTextField(
    email: String,
    onEmailTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    BeThereTextField(
        text = email,
        onTextChange = onEmailTextChange,
        label = stringResource(id = R.string.email),
        leadingIcon = R.drawable.ic_email,
        keyBoardType = KeyboardType.Email,
        modifier = modifier
    )
}