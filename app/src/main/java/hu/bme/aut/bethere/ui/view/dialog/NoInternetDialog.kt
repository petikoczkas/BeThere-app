package hu.bme.aut.bethere.ui.view.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun NoInternetDialog(
    isConnected: Boolean
) {
    if (!isConnected) {
        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                    .padding(MaterialTheme.beThereDimens.gapNormal),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wifi_off),
                    contentDescription = null,
                    tint = MaterialTheme.colors.error,
                    modifier = Modifier.padding(bottom = MaterialTheme.beThereDimens.gapMedium)
                )
                Text(
                    text = stringResource(R.string.no_net),
                    style = MaterialTheme.beThereTypography.noNetDialogTextStyle,
                    modifier = Modifier.padding(top = MaterialTheme.beThereDimens.gapSmall)
                )
            }
        }
    }
}