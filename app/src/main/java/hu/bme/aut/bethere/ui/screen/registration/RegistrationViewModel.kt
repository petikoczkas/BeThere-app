package hu.bme.aut.bethere.ui.screen.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationLoaded
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationSuccess
import hu.bme.aut.bethere.utils.isValidEmail
import hu.bme.aut.bethere.utils.isValidPassword
import hu.bme.aut.bethere.utils.passwordMatches
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegistrationUiState>(
        RegistrationLoaded(
            email = "",
            firstName = "",
            lastName = "",
            password = "",
            passwordAgain = "",
        )
    )
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _registrationFailedEvent =
        MutableStateFlow(RegistrationFailure(isRegistrationFailed = false, exception = null))
    val registrationFailedEvent = _registrationFailedEvent.asStateFlow()

    fun onEmailChange(emailAddress: String) {
        _uiState.update { (_uiState.value as RegistrationLoaded).copy(email = emailAddress) }
    }

    fun onFirstNameChange(firstName: String) {
        _uiState.update { (_uiState.value as RegistrationLoaded).copy(firstName = firstName) }
    }

    fun onLastNameChange(lastName: String) {
        _uiState.update { (_uiState.value as RegistrationLoaded).copy(lastName = lastName) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { (_uiState.value as RegistrationLoaded).copy(password = password) }
    }

    fun onPasswordAgainChange(passwordAgain: String) {
        _uiState.update { (_uiState.value as RegistrationLoaded).copy(passwordAgain = passwordAgain) }
    }

    fun isButtonEnabled(): Boolean {
        if (!(_uiState.value as RegistrationLoaded).email.isValidEmail()
            or (_uiState.value as RegistrationLoaded).firstName.isBlank()
            or (_uiState.value as RegistrationLoaded).lastName.isBlank()
            or !(_uiState.value as RegistrationLoaded).password.isValidPassword()
            or !(_uiState.value as RegistrationLoaded).passwordAgain.isValidPassword()
            or !(_uiState.value as RegistrationLoaded).password.passwordMatches((_uiState.value as RegistrationLoaded).passwordAgain)
        ) return false
        return true
    }

    fun buttonOnClick() {
        val email = (_uiState.value as RegistrationLoaded).email
        val password = (_uiState.value as RegistrationLoaded).password
        viewModelScope.launch {
            try {
                beTherePresenter.registrate(
                    email = email,
                    password = password,
                    user = User(
                        name = "${(_uiState.value as RegistrationLoaded).firstName} ${(_uiState.value as RegistrationLoaded).lastName}"
                    ),
                    onSuccess = {
                        _uiState.value = RegistrationSuccess
                    },
                    onFailure = {
                        _registrationFailedEvent.value =
                            RegistrationFailure(isRegistrationFailed = true, exception = it)
                    }
                )
            } catch (e: Exception) {
                _registrationFailedEvent.value = RegistrationFailure(
                    isRegistrationFailed = true,
                    exception = e
                )
            }
        }
    }

    fun handledRegistrationFailedEvent() {
        _uiState.update {
            (_uiState.value as RegistrationLoaded).copy(
                email = "",
                password = "",
                passwordAgain = "",
            )
        }
        _registrationFailedEvent.update { _registrationFailedEvent.value.copy(isRegistrationFailed = false) }
    }

    data class RegistrationFailure(
        val isRegistrationFailed: Boolean, val exception: Exception?
    )
}