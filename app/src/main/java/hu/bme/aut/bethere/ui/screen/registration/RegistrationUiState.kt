package hu.bme.aut.bethere.ui.screen.registration

sealed class RegistrationUiState {

    data class RegistrationLoaded(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String,
        val passwordAgain: String
    ) : RegistrationUiState()

    object RegistrationSuccess : RegistrationUiState()
}

