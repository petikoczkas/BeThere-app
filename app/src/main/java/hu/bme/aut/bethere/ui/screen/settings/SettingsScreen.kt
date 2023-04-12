package hu.bme.aut.bethere.ui.screen.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsLoaded
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsSaved
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.dialog.BeThereAlertDialog
import hu.bme.aut.bethere.ui.view.dialog.LoadingDialog
import hu.bme.aut.bethere.ui.view.textfield.OutlinedEditTextField

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val updateUserFailedEvent by viewModel.updateUserFailedEvent.collectAsState()
    val showSavingDialog by viewModel.savingState.collectAsState()

    when (uiState) {
        is SettingsLoaded -> {
            val painter = rememberAsyncImagePainter(
                if ((uiState as SettingsLoaded).imageUri == Uri.EMPTY) {
                    viewModel.currentUser.photo
                } else (uiState as SettingsLoaded).imageUri
            )
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    viewModel.onImageChange(it)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = MaterialTheme.beThereDimens.gapVeryLarge),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navigator.popBackStack() },
                            modifier = Modifier
                                .padding(start = MaterialTheme.beThereDimens.gapMedium)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )
                        }
                        Text(
                            text = stringResource(R.string.settings),
                            style = MaterialTheme.beThereTypography.titleTextStyle,
                        )
                        Box(
                            modifier = Modifier
                                .size(
                                    height = MaterialTheme.beThereDimens.gapVeryTiny,
                                    width = MaterialTheme.beThereDimens.gapVeryVeryLarge
                                )
                        )
                    }
                    Card(
                        shape = CircleShape,
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(MaterialTheme.beThereDimens.settingsImageSize)
                            .padding(MaterialTheme.beThereDimens.gapNormal)
                    ) {
                        if (viewModel.currentUser.photo.isEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_person),
                                contentDescription = null,
                                tint = MaterialTheme.colors.background,
                            )
                        } else {
                            Image(
                                painter = painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable { launcher.launch("image/*") },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    OutlinedEditTextField(
                        text = (uiState as SettingsLoaded).name,
                        onTextChange = viewModel::onNameChange,
                        placeholder = stringResource(id = R.string.name),
                        modifier = Modifier
                            .padding(
                                vertical = MaterialTheme.beThereDimens.gapLarge,
                                horizontal = MaterialTheme.beThereDimens.gapNormal
                            )
                    )
                }
                PrimaryButton(
                    onClick = { viewModel.saveButtonOnClick() },
                    text = stringResource(R.string.save),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.beThereDimens.gapNormal)
                )
                if (updateUserFailedEvent.isUpdateUserFailed) {
                    BeThereAlertDialog(
                        title = stringResource(R.string.update_profile_failed),
                        description = updateUserFailedEvent.exception?.message.toString(),
                        onDismiss = { viewModel.handledUpdateUserFailedEvent() }
                    )
                }
                if (showSavingDialog) {
                    LoadingDialog(text = stringResource(R.string.saving))
                }
            }
        }
        is SettingsSaved -> {
            navigator.popBackStack()
        }
    }

}