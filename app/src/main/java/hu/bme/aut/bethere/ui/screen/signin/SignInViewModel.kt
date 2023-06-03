package hu.bme.aut.bethere.ui.screen.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.signin.SignInUiState.SignInLoaded
import hu.bme.aut.bethere.ui.screen.signin.SignInUiState.SignInSuccess
import hu.bme.aut.bethere.utils.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.SignInInit)
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _signInFailedEvent =
        MutableStateFlow(SignInFailure(isLoginFailed = false, exception = null))
    val signInFailedEvent = _signInFailedEvent.asStateFlow()

    private val _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    fun onEmailChange(emailAddress: String) {
        _uiState.update { (_uiState.value as SignInLoaded).copy(email = emailAddress) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { (_uiState.value as SignInLoaded).copy(password = password) }
    }

    fun isButtonEnabled(): Boolean {
        if (!(_uiState.value as SignInLoaded).email.isValidEmail() or (_uiState.value as SignInLoaded).password.isBlank()) return false
        return true
    }

    fun isLoggedIn(): Boolean {
        if (beTherePresenter.isLoggedIn()) return true
        _uiState.value = SignInLoaded("", "")
        return false
    }

    fun buttonOnClick() {
        val email = (_uiState.value as SignInLoaded).email
        val password = (_uiState.value as SignInLoaded).password
        _loginState.value = true
        viewModelScope.launch {
            try {
                beTherePresenter.signIn(
                    email = email,
                    password = password,
                    onSuccess = {
                        _loginState.value = false
                        _uiState.value = SignInSuccess
                    },
                    onFailure = {
                        _loginState.value = false
                        _signInFailedEvent.value =
                            SignInFailure(isLoginFailed = true, exception = it)
                    }
                )
            } catch (e: Exception) {
                _loginState.value = false
                _signInFailedEvent.value = SignInFailure(isLoginFailed = true, exception = e)
            }
        }
    }

    fun handledSignInFailedEvent() {
        _uiState.update { (_uiState.value as SignInLoaded).copy(email = "", password = "") }
        _signInFailedEvent.update { _signInFailedEvent.value.copy(isLoginFailed = false) }
    }

    data class SignInFailure(
        val isLoginFailed: Boolean,
        val exception: Exception?
    )
}