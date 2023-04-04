package hu.bme.aut.bethere.ui.view.checker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun PasswordChecker(
    password: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Password has to contain:",
            style = MaterialTheme.beThereTypography.passwordCheckerTitleTextStyle,
        )
        CheckerRow(text = "At least 8 characters ") {
            if (password.length >= 8) CheckIcon() else CancelIcon()
        }
        CheckerRow(text = "An upper case letter") {
            if (password.contains("[A-Z]".toRegex())) CheckIcon() else CancelIcon()
        }

        CheckerRow(text = "A lower case letter") {
            if (password.contains("[a-z]".toRegex())) CheckIcon() else CancelIcon()
        }
        CheckerRow(text = "A number") {
            if (password.contains("\\d".toRegex())) CheckIcon() else CancelIcon()
        }
    }
}

@Composable
private fun CheckerRow(
    text: String,
    icon: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.padding(top = MaterialTheme.beThereDimens.gapSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Text(
            text = text,
            style = MaterialTheme.beThereTypography.passwordCheckerDescriptionTextStyle,
            modifier = Modifier.padding(start = MaterialTheme.beThereDimens.gapMedium)
        )
    }
}

@Composable
private fun CheckIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_check_circle),
        contentDescription = null,
        tint = MaterialTheme.beThereColors.green
    )
}

@Composable
private fun CancelIcon() {
    Icon(
        painter = painterResource(id = R.drawable.ic_cancel_circle),
        contentDescription = null,
        tint = MaterialTheme.colors.error
    )
}