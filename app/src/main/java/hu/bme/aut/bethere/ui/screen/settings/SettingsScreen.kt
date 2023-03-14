package hu.bme.aut.bethere.ui.screen.settings

import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.screen.destinations.HomeScreenDestination
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.view.button.PrimaryButton
import hu.bme.aut.bethere.ui.view.textfield.DisabledTextField

@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(imageUri.value.ifEmpty { R.drawable.ic_person })
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
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
            DisabledTextField(
                text = "Name",
                modifier = Modifier.padding(
                    vertical = MaterialTheme.beThereDimens.gapLarge,
                    horizontal = MaterialTheme.beThereDimens.gapNormal
                )
            )

        }
        PrimaryButton(
            onClick = { navigator.navigate(HomeScreenDestination) },
            enabled = true,
            text = stringResource(R.string.save),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.beThereDimens.gapLarge,
                    horizontal = MaterialTheme.beThereDimens.gapNormal
                )
        )
    }
}