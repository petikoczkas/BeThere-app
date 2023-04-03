package hu.bme.aut.bethere.ui.screen.settings

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsLoaded
import hu.bme.aut.bethere.ui.screen.settings.SettingsUiState.SettingsSaved
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.textfield.OutlinedEditTextField

@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val updateUserFailedEvent by viewModel.updateUserFailedEvent.collectAsState()


    when (uiState) {
        is SettingsLoaded -> {
            val painter = rememberAsyncImagePainter(
                if ((uiState as SettingsLoaded).imageUri == Uri.EMPTY) {
                    viewModel.currentUser.photo.ifEmpty { R.drawable.ic_person }
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
                    IconButton(
                        onClick = { navigator.popBackStack() },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(
                                start = MaterialTheme.beThereDimens.gapMedium,
                                top = MaterialTheme.beThereDimens.gapNormal
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                    Card(
                        shape = CircleShape,
                        backgroundColor = MaterialTheme.beThereColors.gray,
                        modifier = Modifier
                            .size(250.dp)
                            .padding(MaterialTheme.beThereDimens.gapNormal)
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable { launcher.launch("image/*") },
                            contentScale = ContentScale.Crop
                        )
                    }
                    OutlinedEditTextField(
                        text = (uiState as SettingsLoaded).name,
                        onTextChange = viewModel::onNameChange,
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
                        .padding(
                            vertical = MaterialTheme.beThereDimens.gapLarge,
                            horizontal = MaterialTheme.beThereDimens.gapNormal
                        )
                )
                if (updateUserFailedEvent.isUpdateUserFailed) {
                    viewModel.handledUpdateUserFailedEvent()
                    Toast.makeText(
                        LocalContext.current,
                        "Error updating your profile",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        is SettingsSaved -> {
            navigator.popBackStack()
        }
    }

}