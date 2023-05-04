package hu.bme.aut.bethere.ui.screen.settings

import android.net.Uri


sealed class SettingsUiState {
    object SettingsInit : SettingsUiState()
    data class SettingsLoaded(
        val name: String,
        val imageUri: Uri
    ) : SettingsUiState()

    object SettingsSaved : SettingsUiState()
}
