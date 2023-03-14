package hu.bme.aut.bethere.ui.screen.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.destinations.HomeScreenDestination
import hu.bme.aut.bethere.ui.screen.destinations.RegistrationScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.textfield.EmailTextField
import hu.bme.aut.bethere.ui.view.textfield.PasswordTextField

@RootNavGraph(start = true)
@Destination
@Composable
fun SignInScreen(navigator: DestinationsNavigator) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.beThereDimens.gapNormal),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.beThereTypography.titleTextStyle,
                modifier = Modifier.padding(vertical = MaterialTheme.beThereDimens.gapVeryVeryLarge)
            )
            EmailTextField(
                email = email,
                onEmailTextChange = { email = it },
                modifier = Modifier.padding(vertical = MaterialTheme.beThereDimens.gapNormal)
            )
            PasswordTextField(
                password = password,
                onPasswordTextChange = { password = it },
                modifier = Modifier.padding(vertical = MaterialTheme.beThereDimens.gapNormal)
            )
            Button(
                onClick = { navigator.navigate(RegistrationScreenDestination) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.beThereColors.white,
                    contentColor = MaterialTheme.beThereColors.black
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = MaterialTheme.beThereDimens.gapNone,
                    pressedElevation = MaterialTheme.beThereDimens.gapNone,
                    disabledElevation = MaterialTheme.beThereDimens.gapNone
                )
            ) {
                Text(
                    text = stringResource(R.string.registrate),
                    style = MaterialTheme.beThereTypography.registrateButtonTextStyle
                )
            }
        }
        PrimaryButton(
            onClick = { navigator.navigate(HomeScreenDestination) },
            text = stringResource(R.string.sign_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.beThereDimens.gapLarge),
        )
    }
}