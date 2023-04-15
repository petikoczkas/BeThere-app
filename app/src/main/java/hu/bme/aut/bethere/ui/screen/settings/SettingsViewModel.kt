package hu.bme.aut.bethere.ui.screen.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.bethere.data.model.User
import hu.bme.aut.bethere.ui.BeTherePresenter
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsLoaded
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsSaved
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val beTherePresenter: BeTherePresenter
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<SettingsUiState>(SettingsLoaded(name = "", imageUri = Uri.EMPTY))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    var currentUser = User()


    private val _updateUserFailedEvent =
        MutableStateFlow(UpdateUserFailure(isUpdateUserFailed = false, exception = null))
    val updateUserFailedEvent = _updateUserFailedEvent.asStateFlow()

    private val _savingState = MutableStateFlow(false)
    val savingState = _savingState.asStateFlow()

    init {
        viewModelScope.launch {
            currentUser = beTherePresenter.getCurrentUser()
            _uiState.value = SettingsLoaded(
                name = currentUser.name,
                imageUri = Uri.EMPTY
            )
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { (_uiState.value as SettingsLoaded).copy(name = name) }
    }

    fun onImageChange(image: Uri) {
        _uiState.update { (_uiState.value as SettingsLoaded).copy(imageUri = image) }
    }

    fun saveButtonOnClick() {
        _savingState.value = true
        viewModelScope.launch {
            try {
                currentUser.name = (_uiState.value as SettingsLoaded).name
                if ((_uiState.value as SettingsLoaded).imageUri != Uri.EMPTY) {
                    beTherePresenter.uploadProfilePicture(
                        userId = currentUser.id,
                        imageUri = (_uiState.value as SettingsLoaded).imageUri,
                        onSuccess = {
                            currentUser.photo = it
                            viewModelScope.launch {
                                beTherePresenter.updateUser(currentUser)
                            }
                            _savingState.value = true
                            _uiState.value = SettingsSaved
                        }
                    )
                } else {
                    beTherePresenter.updateUser(currentUser)
                    _savingState.value = true
                    _uiState.value = SettingsSaved
                }
            } catch (e: Exception) {
                _savingState.value = false
                _updateUserFailedEvent.value =
                    UpdateUserFailure(isUpdateUserFailed = true, exception = e)
            }
        }
    }

    fun handledUpdateUserFailedEvent() {
        _updateUserFailedEvent.update { _updateUserFailedEvent.value.copy(isUpdateUserFailed = false) }
    }

    data class UpdateUserFailure(
        val isUpdateUserFailed: Boolean,
        val exception: Exception?
    )
}