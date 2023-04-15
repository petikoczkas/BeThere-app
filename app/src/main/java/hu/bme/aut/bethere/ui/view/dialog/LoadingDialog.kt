package hu.bme.aut.bethere.ui.view.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hu.bme.aut.bethere.ui.theme.beThereDimens

@Composable
fun LoadingDialog(
    text: String
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.surface)
                .padding(MaterialTheme.beThereDimens.gapNormal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(size = MaterialTheme.beThereDimens.dialogProgressIndicatorSize),
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapMedium)
            )
        }
    }
}