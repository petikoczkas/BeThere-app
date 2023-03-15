package hu.bme.aut.bethere.ui.screen.registration

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.common.isValidEmail
import hu.bme.aut.bethere.common.isValidPassword
import hu.bme.aut.bethere.common.passwordMatches
import hu.bme.aut.bethere.service.FirebaseAuthService
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationLoaded
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    //private val firebaseAuth: FirebaseAuth
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
        ) return false
        return true
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun buttonOnClick() {
        val email = (_uiState.value as RegistrationLoaded).email
        val password = (_uiState.value as RegistrationLoaded).password
        if ((_uiState.value as RegistrationLoaded).password.passwordMatches((_uiState.value as RegistrationLoaded).passwordAgain)) {
            FirebaseAuthService.registrate(firebaseAuth = firebaseAuth,
                email = email,
                password = password,
                onSuccess = {
                    _uiState.value = RegistrationSuccess
                },
                onFailure = {
                    _registrationFailedEvent.value =
                        RegistrationFailure(isRegistrationFailed = true, exception = it)
                })
        } else {
            _registrationFailedEvent.value = RegistrationFailure(
                isRegistrationFailed = true,
                exception = Exception("The two passwords are not the same! Try again!")
            )
        }
    }

    fun handledRegistrationFailedEvent() {
        _uiState.update {
            (_uiState.value as RegistrationLoaded).copy(
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