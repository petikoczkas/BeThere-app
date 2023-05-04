package hu.bme.aut.bethere.ui.screen.settings

import com.google.common.truth.Truth.assertThat
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.Before

import org.junit.Test

class SettingsViewModelTest {

    private lateinit var settingsViewModel: SettingsViewModel
    private val beTherePresenter: BeTherePresenter = mockk()

    @Before
    fun setUp() {
        clearAllMocks()
        settingsViewModel = SettingsViewModel(beTherePresenter)
    }

    @Test
    fun setCurrentUser_ReturnsExpected() {
        settingsViewModel.currentUser = User(name = "Test User")
        assertThat(settingsViewModel.currentUser.name).isEqualTo("Test User")
    }

    @Test
    fun getUpdateUserFailedEvent() {
        assertThat(settingsViewModel.updateUserFailedEvent.value.isUpdateUserFailed).isFalse()
    }

    @Test
    fun getSavingState() {
        assertThat(settingsViewModel.savingState.value).isFalse()
    }

    @Test
    fun handledUpdateUserFailedEvent() {
        settingsViewModel.handledUpdateUserFailedEvent()
        assertThat(settingsViewModel.updateUserFailedEvent.value.isUpdateUserFailed).isFalse()
    }
}