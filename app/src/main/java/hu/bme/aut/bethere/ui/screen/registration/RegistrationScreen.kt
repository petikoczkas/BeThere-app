package hu.bme.aut.bethere.ui.screen.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.textfield.BeThereTextField
import hu.bme.aut.bethere.ui.view.textfield.EmailTextField
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.view.textfield.PasswordTextField


@Composable
fun RegistrationScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordAgain by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = MaterialTheme.beThereDimens.gapMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(
                text = stringResource(R.string.registration),
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(
                    top = MaterialTheme.beThereDimens.gapMedium,
                    bottom = MaterialTheme.beThereDimens.gapVeryVeryLarge,
                )
            )
            EmailTextField(
                email = email,
                onEmailTextChange = { email = it },
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
            )
            BeThereTextField(
                text = firstName,
                onTextChange = { firstName = it },
                label = stringResource(id = R.string.first_name),
                leadingIcon = R.drawable.ic_account_circle,
                keyBoardType = KeyboardType.Text,
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal,)
            )
            BeThereTextField(
                text = lastName,
                onTextChange = { lastName = it },
                label = stringResource(id = R.string.last_name),
                leadingIcon = R.drawable.ic_account_circle,
                keyBoardType = KeyboardType.Text,
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal,)
            )
            PasswordTextField(
                password = password,
                onPasswordTextChange = { password = it },
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
            )
            PasswordTextField(
                password = passwordAgain,
                onPasswordTextChange = { passwordAgain = it },
                label = stringResource(id = R.string.password_again),
                modifier = Modifier.padding(MaterialTheme.beThereDimens.gapNormal)
            )
        }
        PrimaryButton(
            onClick = { /*TODO*/ },
            enabled = true,
            text = stringResource(id = R.string.registrate),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.beThereDimens.gapLarge,
                    horizontal = MaterialTheme.beThereDimens.gapNormal
                )
        )
    }
}