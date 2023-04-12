package hu.bme.aut.bethere.ui.view.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import hu.bme.aut.bethere.R

@Composable
fun BeThereAlertDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit = onDismiss
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm)
            { Text(text = stringResource(id = R.string.ok)) }
        },
        title = { Text(text = title) },
        text = { Text(text = description) }
    )
}