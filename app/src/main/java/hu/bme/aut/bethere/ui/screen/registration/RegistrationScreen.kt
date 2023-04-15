package hu.bme.aut.bethere.ui.screen.registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.destinations.RegistrationSuccessScreenDestination
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationLoaded
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationSuccess
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.checker.PasswordChecker
import hu.bme.aut.bethere.ui.view.dialog.BeThereAlertDialog
import hu.bme.aut.bethere.ui.view.textfield.BeThereTextField
import hu.bme.aut.bethere.ui.view.textfield.EmailTextField
import hu.bme.aut.bethere.ui.view.textfield.PasswordTextField

@Destination
@Composable
fun RegistrationScreen(
    navigator: DestinationsNavigator,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val registrationFailedEvent by viewModel.registrationFailedEvent.collectAsState()

    when (uiState) {
        is RegistrationLoaded -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MaterialTheme.beThereDimens.gapVeryLarge),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navigator.popBackStack() },
                            modifier = Modifier
                                .padding(start = MaterialTheme.beThereDimens.gapMedium)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        Text(
                            text = stringResource(R.string.registration),
                            style = MaterialTheme.beThereTypography.titleTextStyle,
                        )
                        Box(
                            modifier = Modifier
                                .size(
                                    height = MaterialTheme.beThereDimens.gapVeryTiny,
                                    width = MaterialTheme.beThereDimens.gapVeryVeryLarge
                                )
                        )
                    }
                    EmailTextField(
                        email = (uiState as RegistrationLoaded).email,
                        onEmailTextChange = viewModel::onEmailChange,
                        modifier = Modifier.padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapLarge
                        )
                    )
                    BeThereTextField(
                        text = (uiState as RegistrationLoaded).firstName,
                        onTextChange = viewModel::onFirstNameChange,
                        label = stringResource(id = R.string.first_name),
                        leadingIcon = R.drawable.ic_account_circle,
                        keyBoardType = KeyboardType.Text,
                        modifier = Modifier.padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapLarge
                        )
                    )
                    BeThereTextField(
                        text = (uiState as RegistrationLoaded).lastName,
                        onTextChange = viewModel::onLastNameChange,
                        label = stringResource(id = R.string.last_name),
                        leadingIcon = R.drawable.ic_account_circle,
                        keyBoardType = KeyboardType.Text,
                        modifier = Modifier.padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapLarge
                        )
                    )
                    PasswordTextField(
                        password = (uiState as RegistrationLoaded).password,
                        onPasswordTextChange = viewModel::onPasswordChange,
                        isPasswordAgain = false,
                        modifier = Modifier.padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapMedium
                        )
                    )
                    PasswordChecker(
                        password = (uiState as RegistrationLoaded).password,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = MaterialTheme.beThereDimens.gapLarge)
                    )
                    PasswordTextField(
                        password = (uiState as RegistrationLoaded).passwordAgain,
                        onPasswordTextChange = viewModel::onPasswordAgainChange,
                        isPasswordAgain = true,
                        firstPassword = (uiState as RegistrationLoaded).password,
                        label = stringResource(id = R.string.password_again),
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.beThereDimens.gapNormal,
                            vertical = MaterialTheme.beThereDimens.gapLarge,
                        )
                    )
                }
                PrimaryButton(
                    onClick = { viewModel.buttonOnClick() },
                    enabled = viewModel.isButtonEnabled(),
                    text = stringResource(id = R.string.registrate),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.beThereDimens.gapNormal,
                            end = MaterialTheme.beThereDimens.gapNormal,
                            bottom = MaterialTheme.beThereDimens.gapNormal
                        )
                )
                if (registrationFailedEvent.isRegistrationFailed) {
                    BeThereAlertDialog(
                        title = stringResource(R.string.registration_failed),
                        description = registrationFailedEvent.exception?.message.toString(),
                        onDismiss = { viewModel.handledRegistrationFailedEvent() }
                    )
                }
            }
        }
        is RegistrationSuccess -> {
            navigator.navigate(RegistrationSuccessScreenDestination)
        }
    }
}