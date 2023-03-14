package hu.bme.aut.bethere.ui.view.textfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import hu.bme.aut.bethere.R
import hu.bme.aut.bethere.ui.theme.beThereColors
import hu.bme.aut.bethere.ui.theme.beThereDimens
import hu.bme.aut.bethere.ui.theme.beThereTypography

@Composable
fun SearchField(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchButtonClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.beThereDimens.gapMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            modifier = Modifier
                .padding(start = MaterialTheme.beThereDimens.gapNormal)
                .weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.beThereColors.darkGray,
                textColor = MaterialTheme.beThereColors.black,
                focusedIndicatorColor = MaterialTheme.beThereColors.transparent,
                unfocusedIndicatorColor = MaterialTheme.beThereColors.transparent,
                disabledIndicatorColor = MaterialTheme.beThereColors.transparent,
                backgroundColor = MaterialTheme.beThereColors.gray,
                cursorColor = MaterialTheme.beThereColors.black
            ),
            textStyle = MaterialTheme.beThereTypography.beThereTextFieldTextStyle,
            shape = RoundedCornerShape(MaterialTheme.beThereDimens.textFieldCornerSize)
        )
        IconButton(
            onClick = { onSearchButtonClick() },
            modifier = Modifier.padding(end = MaterialTheme.beThereDimens.gapMedium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
            )
        }
    }
    Divider(
        color = MaterialTheme.beThereColors.black,
        thickness = MaterialTheme.beThereDimens.dividerThickness,
        modifier = Modifier.padding(horizontal = MaterialTheme.beThereDimens.gapNormal)
    )
}