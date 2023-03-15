package hu.bme.aut.bethere.ui.screen.signin


sealed class SignInUiState {

    data class SignInLoaded(
        val email: String,
        val password: String
    ) : SignInUiState()

    object SignInSuccess : SignInUiState()
}
