package hu.bme.aut.bethere.ui.screen.signin

import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.ui.BeTherePresenter
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {
    private lateinit var signInViewModel: SignInViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        signInViewModel = SignInViewModel(beTherePresenter)
    }

    @Test
    fun isButtonEnabled_ReturnsFalse() {
        /*coEvery { beTherePresenter. } coAnswers {
            delay(1_000)
            dataToLoad
        }*/

        assertThat(signInViewModel.isButtonEnabled()).isFalse()
    }

    @Test
    fun isButtonEnabled_ReturnsTrue() {
        signInViewModel.onEmailChange("test@test.com")
        signInViewModel.onPasswordChange("test")
        assertThat(signInViewModel.isButtonEnabled()).isTrue()
    }

    @Test
    fun onEmailChange_ChangedInUiState() {
        signInViewModel.onEmailChange("test@test.com")
        assertThat(signInViewModel.uiState.value).isEqualTo(
            SignInUiState.SignInLoaded(
                "test@test.com",
                ""
            )
        )
    }

    @Test
    fun onPasswordChange_ChangedInUiState() {
        signInViewModel.onPasswordChange("test")
        assertThat(signInViewModel.uiState.value).isEqualTo(SignInUiState.SignInLoaded("", "test"))
    }

    @Test
    fun handledSignInFailedEvent_ChangedInUiState_and_ChangedIn_SignInFailedEvent() {
        signInViewModel.onEmailChange("test@test.com")
        signInViewModel.onPasswordChange("test")
        assertThat(signInViewModel.uiState.value).isEqualTo(
            SignInUiState.SignInLoaded(
                "test@test.com",
                "test"
            )
        )
        signInViewModel.handledSignInFailedEvent()
        assertThat(signInViewModel.uiState.value).isEqualTo(SignInUiState.SignInLoaded("", ""))
    }
}