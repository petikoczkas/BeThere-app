package hu.bme.aut.bethere.ui.screen.registration

import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.registration.RegistrationUiState.RegistrationLoaded
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before

import org.junit.Test

class RegistrationViewModelTest {

    private lateinit var registrationViewModel: RegistrationViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        registrationViewModel = RegistrationViewModel(beTherePresenter)
    }

    @Test
    fun onEmailChange_ChangedInUiState() {
        registrationViewModel.onEmailChange("test@test.com")
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "test@test.com",
                    "",
                    "",
                    "",
                    ""
                )
            )
    }

    @Test
    fun onFirstNameChange_ChangedInUiState() {
        registrationViewModel.onFirstNameChange("Android")
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "",
                    "Android",
                    "",
                    "",
                    ""
                )
            )
    }

    @Test
    fun onLastNameChange_ChangedInUiState() {
        registrationViewModel.onLastNameChange("Test")
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "",
                    "",
                    "Test",
                    "",
                    ""
                )
            )
    }

    @Test
    fun onPasswordChange_ChangedInUiState() {
        registrationViewModel.onPasswordChange("test")
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "",
                    "",
                    "",
                    "test",
                    ""
                )
            )
    }

    @Test
    fun onPasswordAgainChange_ChangedInUiState() {
        registrationViewModel.onPasswordAgainChange("test")
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "",
                    "",
                    "",
                    "",
                    "test"
                )
            )
    }

    @Test
    fun isButtonEnabled_ReturnsTrue() {
        registrationViewModel.onEmailChange("test@test.com")
        registrationViewModel.onFirstNameChange("Android")
        registrationViewModel.onLastNameChange("Test")
        registrationViewModel.onPasswordChange("123456Aa")
        registrationViewModel.onPasswordAgainChange("123456Aa")

        assertThat(registrationViewModel.isButtonEnabled()).isTrue()
    }

    @Test
    fun isButtonEnabled_ReturnsFalse() {
        assertThat(registrationViewModel.isButtonEnabled()).isFalse()
    }

    @Test
    fun handledRegistrationFailedEvent_ChangedInUiState() {
        registrationViewModel.onEmailChange("test@test.com")
        registrationViewModel.onFirstNameChange("Android")
        registrationViewModel.onLastNameChange("Test")
        registrationViewModel.onPasswordChange("123456Aa")
        registrationViewModel.onPasswordAgainChange("123456Aa")
        registrationViewModel.handledRegistrationFailedEvent()
        assertThat(registrationViewModel.uiState.value)
            .isEqualTo(
                RegistrationLoaded(
                    "",
                    "Android",
                    "Test",
                    "",
                    ""
                )
            )
    }
}