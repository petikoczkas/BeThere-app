package hu.bme.aut.bethere.ui.screen.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.common.isValidEmail
import hu.bme.aut.bethere.service.FirebaseAuthService
import hu.bme.aut.bethere.ui.screen.signin.SignInUiState.SignInLoaded
import hu.bme.aut.bethere.ui.screen.signin.SignInUiState.SignInSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    //private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignInUiState>(
        SignInLoaded(
            email = "", password = ""
        )
    )
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _signInFailedEvent =
        MutableStateFlow(SignInFailure(isLoginFailed = false, exception = null))
    val signInFailedEvent = _signInFailedEvent.asStateFlow()

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

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun buttonOnClick() {
        val email = (_uiState.value as SignInLoaded).email
        val password = (_uiState.value as SignInLoaded).password
        FirebaseAuthService.signIn(
            firebaseAuth = firebaseAuth,
            email = email,
            password = password,
            onSuccess = {
                _uiState.value = SignInSuccess
            },
            onFailure = {
                _signInFailedEvent.value = SignInFailure(isLoginFailed = true, exception = it)
            }
        )
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